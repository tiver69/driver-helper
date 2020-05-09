const darkGreen = "green";
const darkYellow = "#b7b700";
const darkRed = "red";
const lightRed = "#fcc";
const lightYellow = "#fffbcc";
const lightGreen = "#dfc";

const noneData = { color: "grey" };

const greenAlert = { color: lightGreen };
const yellowAlert = { color: lightYellow };
const redAlert = { color: lightRed };

const greenAlertMobile = { color: darkGreen };
const yellowAlertMobile = { color: darkYellow };
const redAlertMobile = { color: darkRed };

const greenAlertPointAnimation = { borderColor: lightGreen };
const yellowAlertPointAnimation = { borderColor: lightYellow };
const redAlertPointAnimation = { borderColor: lightRed };

const greenAlertPoint = { backgroundColor: darkGreen };
const yellowAlertPoint = { backgroundColor: darkYellow };
const redAlertPoint = { backgroundColor: darkRed };

export function getSensorAlertMobile(distValue) {
  return distValue === 0
    ? noneData
    : distValue > 200
    ? greenAlertMobile
    : distValue < 50
    ? redAlertMobile
    : yellowAlertMobile;
}

export function getSensorAlert(distValue) {
  return distValue === 0
    ? noneData
    : distValue > 200
    ? greenAlert
    : distValue < 50
    ? redAlert
    : yellowAlert;
}

export function getSensorAlertPoint(distValue) {
  return distValue > 200
    ? greenAlertPoint
    : distValue < 50
    ? redAlertPoint
    : yellowAlertPoint;
}

export function getSensorAlertPointAnimation(distValue) {
  return distValue > 200
    ? greenAlertPointAnimation
    : distValue < 50
    ? redAlertPointAnimation
    : yellowAlertPointAnimation;
}
