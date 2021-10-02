import React, { useEffect, useState } from "react";
import axios from "axios";

export default function HomeScreen() {
  const [jobs, setJobs] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(false);

  const [scheduled, setScheduled] = useState("false");
  const [jobType, setJobType] = useState("REPORT_GENERATION");
  const [jobPriority, setJobPriority] = useState("LOW");

  useEffect(() => {
    const fetchJobs = async () => {
      try {
        setLoading(true);
        const { data } = await axios.get("/api/v1/jobs");
        console.log(data);
        setLoading(false);
        setJobs(data);
      } catch (err) {
        setError(err.message);
        setLoading(false);
      }
    };
    fetchJobs();
  }, []);

  const createJob = async () => {
    try {
      const { data } = await axios
        .post("/api/v1/jobs/create", {
          headers: {
            "content-type": " application/json",
          },

          name: "job1",
          scheduled: scheduled,
          jobType: jobType,
          jobPriority: jobPriority,
        })
        .then((res) => {
          console.log("my call", res);
        });
      console.log(data);
    } catch (err) {}
  };

  const refresh = async () => {
    try {
      setLoading(true);
      const { data } = await axios.get("/api/v1/jobs");
      setLoading(false);
      setJobs(data);
      console.log(data);
    } catch (err) {
      setError(err.message);
      setLoading(false);
    }
  };

  return (
    <div>
      <div className="button-margin">
        <button onClick={refresh}>Refresh</button>
        <button onClick={createJob}>Create a job</button>
        <label>
          Job Type :
          <select value={jobType} onChange={(e) => setJobType(e.target.value)}>
            <option value="REPORT_GENERATION">Report Creation</option>
            <option value="MAIL_REPORT">Mail Report</option>
            <option value="MIGRATE_DATA">Migrate Data</option>
          </select>
        </label>
        <label>
          Job Priority :
          <select
            value={jobPriority}
            onChange={(e) => setJobPriority(e.target.value)}
          >
            <option value="HIGH">High</option>
            <option value="MEDIUM">Medium</option>
            <option value="LOW">Low</option>
          </select>
        </label>
        <label>
          Schedule Job :
          <select
            value={scheduled}
            onChange={(e) => setScheduled(e.target.value)}
          >
            <option value="false">No</option>
            <option value="true">Yes</option>
          </select>
        </label>
      </div>

      {loading ? (
        <div className="loading">
          <i className="fa fa-spinner fa-spin"></i>
        </div>
      ) : error ? (
        <div className="loading">
          <div>Something went wrong</div>
        </div>
      ) : (
        <div className="row center">
          <table>
            <thead>
              <tr>
                <th>Name</th>
                <th>Scheduled</th>
                <th>Job Status</th>
                <th>Job Priority</th>
              </tr>
            </thead>
            <tbody>
              {jobs.map((job) => (
                <tr key={job.id}>
                  <td>{job.name}</td>
                  <td>{job.scheduled ? "Yes" : "No"}</td>
                  <td>{job.jobStatus}</td>
                  <td>{job.jobPriority}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}
