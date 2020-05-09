import axios from "axios";
import { GET_ALL_CARS, GET_MAPPED_ERRORS, GET_SETTINGS } from "./types";
// import { async } from "q";

export const setUpActiveCarAndGetGarageSettings = carId => async dispatch => {
  const res = await axios.get(`/api/settings?carId=${carId}`);
  dispatch({
    type: GET_SETTINGS,
    payload: res.data
  });
};

export const updateSettings = settings => async dispatch => {
  try {
    await axios.patch("/api/settings", settings);
    dispatch({
      type: GET_MAPPED_ERRORS,
      payload: {}
    });
  } catch (err) {
    dispatch({
      type: GET_MAPPED_ERRORS,
      payload: err.response.data
    });
  }
};

export const getAllCars = () => async dispatch => {
  const res = await axios.get("/api/settings/cars");
  dispatch({
    type: GET_ALL_CARS,
    payload: res.data
  });
};
