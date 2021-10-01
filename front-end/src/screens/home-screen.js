import React, { useEffect, useState } from "react";
import axios from "axios";
import { data } from "../constants/data";

export default function HomeScreen() {
  const [jobs, setJobs] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(false);

  useEffect(() => {
    const fetchJobs = async () => {
      try {
        setLoading(true);
        //const { data } = await axios.get("/api/v1/jobs");
        setLoading(false);
        setJobs(data);
        console.log(data);
      } catch (err) {
        setError(err.message);
        setLoading(false);
      }
    };
    fetchJobs();
  }, []);
  return (
    <div>
      <div className="row">
        <button>Create a job</button>
        <button>Create a job</button>
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
          {jobs.map((job) => (
            <div>{job.id}</div>
          ))}
        </div>
      )}
    </div>
  );
}
