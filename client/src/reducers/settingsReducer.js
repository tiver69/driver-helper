import { GET_ALL_CARS, SET_ACTIVE_CAR, GET_SETTINGS } from "../actions/types";

const initialState = {
  allCars: [],
  currentCar: {},
  garageConfig: {}
};

export default function(state = initialState, action) {
  switch (action.type) {
    case GET_ALL_CARS:
      return {
        ...state,
        allCars: action.payload
      };
    case SET_ACTIVE_CAR:
      return {
        ...state,
        currentCar: action.payload
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
