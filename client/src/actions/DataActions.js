import axios from "axios";
import { GET_DATA, POST_RESET_DATA } from "./types";
// import { async } from "q";

export const getDataNode = () => async dispatch => {
  const res = await axios.get("/api/data");
  dispatch({
    type: GET_DATA,
    payload: res.data
  });
};

export const postResetData = () => async dispatch => {
  const res = await axios.post("/api/data/reset");
  dispatch({
    type: POST_RESET_DATA,
    payload: res.data
  });
};
