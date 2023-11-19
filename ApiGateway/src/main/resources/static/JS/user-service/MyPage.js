import * as callServer from './Call/MyPageFetch.js';
import * as addEle from './Element/addElement.js';
import * as addEvent from './Event/MyPageEvent.js';
import * as CookieFunc from '../common/Cookie.js';

//리뷰 박스에 관한 뮤테이터 옵저버를 통한 로딩(waiting) 구현 필요
//리뷰 모달의 텍스트에어리어 리사이즈 옵저버를 통한 로딩(waiting) 구현 필요

const userId =  CookieFunc.getCookie('useridCookie');

window.onload = async function () {
    //get api 호출
    const userInfos = await callServer.getMyInfo(userId);
    const userInfo = {
        email: userInfos.email,
        name: userInfos.name,
        phoneNumber: userInfos.phoneNumber,
    }
    const reviewList = userInfos.myReviewList;

    addEle.addUserInfo(userInfo);
    addEle.addReviewInfo(reviewList);

    addEvent.myInfoModalEvent();
    addEvent.reviewModalEvent();
    addEvent.updateBtnEvent(userId);


}

