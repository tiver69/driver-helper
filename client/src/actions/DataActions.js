import axios from "axios";
import {
  GET_DATA,
  GET_SENSOR_DATA,
  POST_RESET_DATA,
  GET_STRING_ERROR
} from "./types";
// import { async } from "q";

export const getDataNode = () => async dispatch => {
  try {
    const res = await axios.get("/api/data");
    dispatch({
      type: GET_DATA,
      payload: res.data
    });
    dispatch({
      type: GET_STRING_ERROR,
      payload: ""
    });
  } catch (err) {
    dispatch({
      type: GET_STRING_ERROR,
      payload: err.response.data
    });
  }
};

export const getSensorDataNode = () => async dispatch => {
  const res = await axios.get("/api/data/sensor-node");
  dispatch({
    type: GET_SENSOR_DATA,
    payload: res.data
  });
};

export const postResetData = () => async dispatch => {
  const res = await axios.post("/api/data/reset");
  dispatch({
    type: POST_RESET_DATA,
    payload: res.data
  });
  dispatch({
    type: GET_STRING_ERROR,
    payload: ""
  });
};
