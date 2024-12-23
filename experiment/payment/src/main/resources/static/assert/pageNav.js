const paSize = $("paSize") // 调整大小的选择框
const paNum = $("paNum") // 分页条(存储页码)
const dataNumSpan = $("dataNum") // 数据长度展示
const addPa = $("addPa") // 向前翻页按钮
const reducePa = $("reducePa") // 向后翻页按钮
let nowPage = 1; // 当前页码
let pageNumMax = 1; // 当前页码最大值
/**
 * 初始化分页条
 * @param dataNum 数据长度
 * @param pageNum 页码数
 */
const firstLoadPa = (dataNum, pageNum) => {
    dataNumSpan.innerText = dataNum
    pageNumMax = pageNum
    paNum.innerHTML = `<span onclick="jumpPage(${pageNum})" style="width:64px;border-radius:12px">${nowPage}/${pageNum}</span>`
}

/**
 * 点击页码的输入框
 * @param pageNum 页码最大值
 */
const jumpPage = (pageNum) => {
    let num = prompt(`输入要跳转页码(最大${pageNum})`, "1");
    if (num == null || num === '0') return;
    num = Number(num); // 将输入的字符串转换为数字格式
    cutPage(num)
}

/**
 * 跳转页码操作(也是分页条核心方法)
 * @param paNum 当前页码的索引
 */
const cutPage = (paNum) => {
    if (paNum === nowPage) return;
    nowPage = paNum
    getAll()
    // 处理前进后退按钮
    addPa.style.display = (paNum === 1) ? 'none' : 'inline-block'
    reducePa.style.display = (paNum === pageNumMax) ? 'none' : 'inline-block'
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
        if (nowPage === pageNumMax) return;
        cutPage(nowPage + 1)
    }
}