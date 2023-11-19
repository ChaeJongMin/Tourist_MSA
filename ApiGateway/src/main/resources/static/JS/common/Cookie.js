export function getCookie(name) {
    console.log("받아온 쿠키 명 : "+name);
    let cookies = document.cookie.split(';');
    for (let i = 0; i < cookies.length; i++) {
        let cookie = cookies[i].trim();
        let parts = cookie.split('=');
        console.log("쿠키 이름 : "+parts[0]);
        if (parts[0] === name) {
            return parts[1];
        }
    }
    return null;
}