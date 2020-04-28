import { GET_ALL_CARS } from "../actions/types";

const initialState = {
  allCars: []
};

export default function(state = initialState, action) {
  switch (action.type) {
    case GET_ALL_CARS:
      return {
        ...state,
        allCars: action.payload
      };

    default:
      return state;
  }
}
