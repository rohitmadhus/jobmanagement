import React, { useEffect, useState } from "react";
import axios from "axios";

export default function HomeScreen() {
  const [jobs, setJobs] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(false);

  useEffect(() => {
    const fetchJobs = async () => {
      try {
        setLoading(true);
        const { data } = await axios.get("/api/v1/jobs");
        setLoading(false);
        setJobs(data);
      } catch (err) {
        setError(err.message);
        setLoading(false);
      }
    };
    fetchJobs();
  }, []);

  const createJob = async (name, scheduled, jobType, jobPriority) => {
    try {
      const { data } = await axios.post("/api/v1/jobs/create", {
        name,
        scheduled,
        jobType,
        jobPriority,
      });
      console.log(data);
    } catch (err) {}
  };

  const createScheduledJob = () => {
    console.log("created a scheduled job");
  };

  const refresh = () => {
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
      <div className="center">
        <button onClick={refresh}>Refresh</button>
        <button onClick={createJob}>Create a job</button>
        <button onClick={createScheduledJob}>Create a scheduled job</button>
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
        <div className="center">
          <table>
            <tbody>
              {jobs.map((job) => (
                <tr key={job.id}>
                  <td>{job.name}</td>
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
