"use strict";
const hedBtn = document.getElementById("headNav")?.getElementsByTagName("button");
const tabDiv = document.getElementsByClassName("tableDiv");
let nowSelNav = 0;
const cikNav = (dataIndex) => {
    if (dataIndex === nowSelNav)
        return;
    hedBtn[dataIndex].classList.add("selectNav");
    hedBtn[nowSelNav].classList.remove("selectNav");
    tabDiv[dataIndex].style.display = 'block';
    tabDiv[nowSelNav].style.display = 'none';
    nowSelNav = dataIndex;
};
const geCopy = (text) => {
    if (text !== "") {
        navigator.clipboard.writeText(text).then(() => alert("复制成功"));
    }
};