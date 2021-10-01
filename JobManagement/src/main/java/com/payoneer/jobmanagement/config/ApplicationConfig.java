package com.payoneer.jobmanagement.config;

import com.payoneer.jobmanagement.constants.CreditCardUserParameter;
import com.payoneer.jobmanagement.models.CreditCardUser;
import com.payoneer.jobmanagement.util.MailUtil;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableBatchProcessing
public class ApplicationConfig {


    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final MongoTemplate mongoTemplate;
    private final MailUtil mailUtil;

    public ApplicationConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, MongoTemplate mongoTemplate, MailUtil mailUtil) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.mongoTemplate = mongoTemplate;
        this.mailUtil = mailUtil;
    }

    @Bean
    public MongoItemReader<CreditCardUser> reader(){
        MongoItemReader<CreditCardUser> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setQuery("{}");
        reader.setTargetType(CreditCardUser.class);
        reader.setSort(new HashMap<String, Sort.Direction>(){{
            put("_id", Sort.Direction.DESC);
        }});
        return reader;
    }

    /**
    *  object to xml -> Marshalling
    *  apis -> JAXB,STAX
    * */
    @Bean
    public StaxEventItemWriter<CreditCardUser> writer(){
        StaxEventItemWriter<CreditCardUser> writer = new StaxEventItemWriter<>();
        writer.setRootTagName("CreditCardUsers");
        writer.setResource(new FileSystemResource("xml/ccUsers.xml"));
        writer.setMarshaller(marshaller());
        return writer;
    }

    private XStreamMarshaller marshaller(){
         XStreamMarshaller marshaller = new XStreamMarshaller();
         Map<String,Class> map = new HashMap<>();
         map.put("CreditCardUser",CreditCardUser.class);
         marshaller.setAliases(map);
         return marshaller;

    }

    @Bean
    public Step step1(){
        return stepBuilderFactory.get("step-1").<CreditCardUser,CreditCardUser>chunk(10).reader(reader()).processor(process()).writer(writer()).build();
    }

    public Job runJob(){
        return jobBuilderFactory.get("report generation").flow(step1()).end().build();
    }

    /**
     *  The process condition is created and will send mail for pending user
     *
     */
    public ItemProcessor<CreditCardUser,CreditCardUser> process(){
        ItemProcessor<CreditCardUser,CreditCardUser> process = new ItemProcessor<CreditCardUser, CreditCardUser>() {
            @Override
            public CreditCardUser process(CreditCardUser creditCardUser) throws Exception {
                if(creditCardUser.getStatus().equals(CreditCardUserParameter.BILL_STATUS.pending)) {
                String msg = mailUtil.sendEmail(creditCardUser.getEmail(),buildMessage(creditCardUser));
                System.out.println(msg);
                return  creditCardUser;
                }

            return null;
            }

            private String buildMessage(CreditCardUser creditCardUser) {
                return "Dear user\n\nyour credit bill has been generated \n\nCard number :" +
                        creditCardUser.getCcNumber()+ "\n\nBill Amount : " +
                        creditCardUser.getBillAmount() + "\n\nPlease pay before due date";
            }
        };
        return process;
     };
}
