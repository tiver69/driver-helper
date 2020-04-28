import axios from "axios";
import { GET_ALL_CARS } from "./types";
// import { async } from "q";

export const getAllCars = () => async dispatch => {
  const res = await axios.get("/api/car/all");
  dispatch({
    type: GET_ALL_CARS,
    payload: res.data
  });
};
