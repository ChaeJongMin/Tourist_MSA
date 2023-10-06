// import { getMapData } from './callServer.js';
import * as callSever from './callServer.js';
import * as SearchFunc from './Search.js';
import * as ReviewFunc from './Review.js'

const HOME_PATH = "../IMG/Etc/";
const detailShowEle=document.querySelector('.detailShow');

let touristNmArr=[];
let currentMarker = null;
let forSearchVisible=false;
let markerEleArr=[];
let certainMakrerEle;

function isVisibleDetailEle(markerEle, markerData) {
    // console.log("markerEle: "+markerEle.data.tourDestNm);
    if(forSearchVisible===true){
        detailShowEle.style.display = 'block';
        currentMarker = markerEle;
    }
    else {
        if (currentMarker === markerEle) {
            // 현재 마커와 같은 마커를 클릭하면 detailShow 숨김
            detailShowEle.style.display = 'none';
            currentMarker = null;
        } else {
            // 다른 마커를 클릭하면 해당 마커의 정보를 표시하고 currentMarker 업데이트
            detailShowEle.style.display = 'block';
            currentMarker = markerEle;
        }
    }
    setInfo(markerData);
}

window.onload = async function () {
    const map = new naver.maps.Map('map',{
        center : new naver.maps.LatLng(35.126033,126.831302),
        maxZoom : 19,
        minZoom : 15
    });

    try {
        const responseData = await callSever.getMapData();
        const resultMarkers=makeMarker(responseData, map);
        touristNmArr=setTouristNmList(responseData);
        resultMarkers.forEach(marker => {
           let markerEle=new naver.maps.Marker(marker);
           markerEleArr.push(markerEle);
           naver.maps.Event.addListener(markerEle, "click", function (e) {
               // 클릭된 마커의 데이터 객체를 가져와서 정보를 표시
               const markerData = markerEle.data;
               isVisibleDetailEle(markerEle, markerData);

               // if (document.querySelector('.detailShow').style.display === 'block') {
               //     console.log("detailShowEle.style.display === 'block'");
               //     ReviewFunc.removeReview();
               //     const responseReviewData = await callSever.getReviewToTourist(markerData.tourDestNm);
               //     ReviewFunc.setReviewData(responseReviewData);
               //     ReviewFunc.resetReviewHeight();
               // }
           });
        });
        setSearchAndCancelEvent(map);
        //찾기, 삭제 버튼 클릭 이벤트 설정
    } catch (error) {
        console.error('데이터 가져오기 실패:', error);
    }
}
function makeMarker(data,map) {
    const markers=[];
    data.forEach(ele => {

        const Lat=ele.lat;
        const Lng=ele.lng;
        let finalyUrl=HOME_PATH+setUrl(ele.visitCnt);
        const marker={
            position : new naver.maps.LatLng(Lat,Lng),
            map:map,
            icon : {
                url : finalyUrl,
                size: new naver.maps.Size(60, 104),
                origin: new naver.maps.Point(0, 0),
                anchor: new naver.maps.Point(25, 26)
            },
            data : ele,
        };
        markers.push(marker);
    });
    return markers;
}
function setUrl(cnt){
    let result;
    if(cnt>999 && cnt<10000){
        result="third";
    } else if(cnt>9999 && cnt<100000){
        result="second";
    } else {
        result="first";
    }
    return result+".png";
}
function setInfo(data){
    const touristNameA = document.querySelector("#touristNameA");
    const visitCnts = document.querySelector("#visitCnts");
    const address = document.querySelector("#address .info-text");
    const intro = document.querySelector("#intro .info-text");
    const parking = document.querySelector("#parking .info-text");
    const capacity = document.querySelector("#capacity .info-text");
    const contact = document.querySelector("#contact .info-text");

    // 각 요소에 텍스트를 설정합니다.
    touristNameA.textContent = data.tourDestNm;
    visitCnts.textContent = data.visitCnt+"명 방문";
    address.textContent = data.addrRoad
    intro.textContent = data.tourDestIntro
    parking.textContent = data.availParkingCnt
    capacity.textContent = data.capacity
    contact.textContent = data.mngAgcTel

    const searchTerm = data.tourDestNm; // 여기에 원하는 검색어나 문자열을 넣으세요.

    // <a> 태그를 선택합니다.
    const googleLink = document.querySelector("#googleLink");

    // href 속성을 동적으로 설정합니다.
    googleLink.href = "https://www.google.com/search?q=" + encodeURIComponent(searchTerm);

    const touristImg=document.querySelector('.touristImg');
    touristImg.src="/IMG/Tourist/"+data.tourDestNm+".jpg";
}

function setSearchAndCancelEvent(map){
    const searchEle=document.querySelector('#searchBtn');
    const cancelEle=document.querySelector('#cancelBtn');
    SearchFunc.setSearchEvent(touristNmArr);
    if(searchEle != null){
        searchEle.addEventListener('click', async function () {
            const touristNm = document.querySelector('#inputLocation').value;
            const singleTouristData = await callSever.getSpecificMapData(touristNm);
            // const SearchedMarker = markerEleArr.filter(marker => marker.data.tourDestNm === singleTouristData.tourDestNm);
            const SearchedMarker = markerEleArr.find(markerEle => {
                return markerEle.data.tourDestNm === singleTouristData.tourDestNm;

            });
            // console.log(JSON.stringify(SearchedMarker, null, 2));
            forSearchVisible=true;
            isVisibleDetailEle(SearchedMarker,singleTouristData);
            forSearchVisible=false;
            map.setCenter( new naver.maps.LatLng(singleTouristData.lat, singleTouristData.lng));
            document.querySelector('#inputLocation').value="";
            SearchFunc.removeLiEle();
        });
    }
    if(cancelEle != null){
        cancelEle.addEventListener('click', function () {
            document.querySelector('#inputLocation').value="";
            if(detailShowEle.style.display === 'block'){
                detailShowEle.style.display = 'none';
                currentMarker = null;
            }
        } );
    }
}
function setTouristNmList(data){
    return data.map(item => {
        return item.tourDestNm;
    });
}
async function setReviewFunc(){

}