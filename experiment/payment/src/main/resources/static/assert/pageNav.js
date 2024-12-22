const paSize = $("paSize") // 调整大小的选择框
const paNum = $("paNum") // 分页条(存储页码)
const dataNumSpan = $("dataNum") // 数据长度展示
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
    paNum.innerHTML = `<span onclick="jumpPage(${pageNum})">${nowPage}</span>`
}

/**
 * 点击页码的输入框
 * @param pageNum 页码最大值
 */
const jumpPage = (pageNum) => {
    let num = prompt(`输入要跳转页码(最大${pageNum})`, "1");
    // 提示用户输入一个数字，如果用户不输入任何内容，输入字段默认会显示"1"
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