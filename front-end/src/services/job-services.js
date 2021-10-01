import axios from "axios";

export const getJobs = async () => {
  const { data } = await axios.get("/api/v1/jobs");
  return data;
};
