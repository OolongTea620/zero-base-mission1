const setMap = (lat, lnt) => {
  let inputLat = document.forms['location']['lat'];
  let inputLnt = document.forms['location']['lnt'];

  inputLat.setAttribute('value',lat);
  inputLnt.setAttribute('value', lnt);
}
const geoSuccess = (position) => {
  const lat = position.coords.latitude;
  const lnt = position.coords.longitude;
  setMap(lat, lnt);
}
const getError = () => {
  alert("내 위치 가져오기 실패");
}

document.getElementById('get-location-btn').onclick = function () {
  navigator.geolocation.getCurrentPosition(geoSuccess, getError);
}