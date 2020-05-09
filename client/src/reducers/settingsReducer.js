import { GET_ALL_CARS, GET_SETTINGS } from "../actions/types";

const initialState = {
  allCars: [],
  curentCar: {},
  garageConfig: {}
};

export default function(state = initialState, action) {
  switch (action.type) {
    case GET_ALL_CARS:
      return {
        ...state,
        allCars: action.payload
      };
    case GET_SETTINGS:
      return {
        ...state,
        garageConfig: action.payload
      };

    default:
      return state;
  }
}
