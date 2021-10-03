package com.payoneer.jobmanagement.config;

import com.mongodb.MongoException;
import com.payoneer.jobmanagement.constants.CreditCardUserParameter;
import com.payoneer.jobmanagement.models.CreditCardUser;
import com.payoneer.jobmanagement.models.JobFlow;
import com.payoneer.jobmanagement.util.MailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.util.*;

@Configuration
@EnableBatchProcessing
public class JobConfig {

    private final Logger logger = LoggerFactory.getLogger(JobConfig.class);

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final MongoTemplate mongoTemplate;
    private final MailUtil mailUtil;

    public static PriorityQueue<JobFlow> pq = new PriorityQueue(10, Comparator.comparing(JobFlow::getJobPriority));
    public static boolean queueMode = false;


    public JobConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, MongoTemplate mongoTemplate, MailUtil mailUtil) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.mongoTemplate = mongoTemplate;
        this.mailUtil = mailUtil;
    }

    @Bean
    @StepScope
    public MongoItemReader<CreditCardUser> reader() throws ParseException, NonTransientResourceException, MongoException {
        MongoItemReader<CreditCardUser> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setQuery("{}");
        reader.setTargetType(CreditCardUser.class);
        reader.setSort(new HashMap<String, Sort.Direction>() {{
            put("_id", Sort.Direction.DESC);
        }});
        return reader;
    }

    /**
     * object to xml -> Marshalling
     * apis -> JAXB,STAX
     */
    @Bean(destroyMethod = "")
    @StepScope
    public StaxEventItemWriter<CreditCardUser> writer() throws Exception {
        StaxEventItemWriter<CreditCardUser> writer = new StaxEventItemWriter<>();
        writer.setRootTagName("CreditCardUsers");
        writer.setResource(new FileSystemResource("xml/ccUsers" + new Date() + ".xml"));
        writer.setMarshaller(marshaller());
        return writer;
    }

    private XStreamMarshaller marshaller() {
        XStreamMarshaller marshaller = new XStreamMarshaller();
        Map<String, Class> map = new HashMap<>();
        map.put("CreditCardUser", CreditCardUser.class);
        marshaller.setAliases(map);
        return marshaller;

    }

    /**
     * chunk size is set to 10 that is 10 data per transaction
     */

    @Bean
    public Step step1() throws Exception {
        return stepBuilderFactory.get("step1").<CreditCardUser, CreditCardUser>chunk(10).reader(reader()).processor(process()).writer(writer()).allowStartIfComplete(true).build();
    }

    @Bean
    public Step step2() throws Exception {
        return stepBuilderFactory.get("step2").<CreditCardUser, CreditCardUser>chunk(10).reader(reader()).writer(writer()).allowStartIfComplete(true).build();
    }

    @Bean(name = "runReportCreationJob")
    public Job runReportCreationJob() throws Exception {
        return jobBuilderFactory.get("processJob")
                .incrementer(new RunIdIncrementer()).listener(listener())
                .flow(step1()).end().build();
    }

    @Bean(name = "report")
    public Job createReport() throws Exception {
        return jobBuilderFactory.get("processJob")
                .incrementer(new RunIdIncrementer()).listener(listener())
                .flow(step2()).end().build();
    }


    /**
     * The process condition is created and will send mail for pending users
     */
    @Bean
    @StepScope
    public ItemProcessor<CreditCardUser, CreditCardUser> process() throws Exception {
        ItemProcessor<CreditCardUser, CreditCardUser> process = new ItemProcessor<CreditCardUser, CreditCardUser>() {
            @Override
            public CreditCardUser process(CreditCardUser creditCardUser) throws Exception {
                if (creditCardUser.getStatus().equals(CreditCardUserParameter.BILL_STATUS.pending)) {
                    String msg = mailUtil.sendEmail(creditCardUser.getEmail(), buildMessage(creditCardUser));
                    System.out.println(msg);
                    return creditCardUser;
                }

                return null;
            }

            private String buildMessage(CreditCardUser creditCardUser) {
                return "Dear user\n\nyour credit bill has been generated \n\nCard number :" +
                        creditCardUser.getCcNumber() + "\n\nBill Amount : " +
                        creditCardUser.getBillAmount() + "\n\nPlease pay before due date";
            }
        };
        return process;
    }

    @Bean
    public JobExecutionListenerSupport listener() {
        return new JobExecutionListenerSupport() {
            @Override
            public void afterJob(JobExecution jobExecution) {
                System.out.println(jobExecution.getStatus());
                if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
                    logger.info("BATCH JOB COMPLETED SUCCESSFULLY");
                }
                if (jobExecution.getStatus() == BatchStatus.ABANDONED || jobExecution.getStatus() == BatchStatus.FAILED || jobExecution.getStatus() == BatchStatus.STOPPED) {
                    logger.info("BATCH JOB FAILED");
                }
            }
        };
    }

}
