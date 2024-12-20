const paSize = $("paSize") // 调整大小的选择框
const pageStrip = $("pageStrip") // 分页条(存储页码)
const dataNumSpan = $("dataNum") // 分页条(存储页码)
let nowPage = 1; // 当前页码
/**
 * 初始化分页条
 * @param dataNum 数据长度
 * @param pageNum 页码数
 */
const firstLoadPa = (dataNum, pageNum) => {
    pageStrip.innerHTML = ''
    dataNumSpan.innerText = dataNum
    for (let i = 1; i < pageNum + 1; i++) {
        pageStrip.innerHTML += `<span onclick="cutPage(${i})">${i}</span>`
    }
    // 选中指定子元素
    const paOneList = pageStrip.getElementsByTagName("span");
    paOneList[nowPage - 1].classList.add("active")
}

/**
 * 点击页码操作(也是分页条核心方法)
 * @param paNum 当前页码的索引
 */
const cutPage = (paNum) => {
    if (paNum === nowPage) return;
    nowPage = paNum
    getAll()
}

/**
 * 向前后翻页
 * @param isLeft
 */
const addPaNum = (isLeft) => {
    if (isLeft) {
        if (nowPage === 1) return;
        cutPage(nowPage - 1)
    } else {
        if (nowPage === pageStrip.getElementsByTagName("span").length) return;
        cutPage(nowPage + 1)
    }
}