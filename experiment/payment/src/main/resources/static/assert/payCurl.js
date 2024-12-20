window.onload = () => {
    getPayType()
    getAll()
};

/**
 * 组装支付类型下拉框
 */
let paymentTypeInOp = ""; // 收入列表
let paymentTypeOutOp = ""; // 支出列表
const paymentTypeSel = $('paymentType')
const getPayType = () => {
    fetch('/paymentDict/option')
        .then(response => response.json())
        .then(resp => {
            for (const respElement of resp["inList"])
                paymentTypeInOp += getOpGroup(respElement)
            for (const respElement of resp["outList"])
                paymentTypeOutOp += getOpGroup(respElement)
            paymentTypeSel.innerHTML = paymentTypeOutOp
        })
        .catch((error) => console.error('Error:', error));
};
const getOpGroup = (respElement) => {
    const {typeName, paymentDictList} = respElement
    let optGroup = `<optgroup label="${typeName}">`
    for (const paymentDict of paymentDictList) {
        const {paymentType, keyName} = paymentDict
        optGroup += `<option value="${paymentType}">${keyName}</option>`
    }
    optGroup += `</optgroup>`
    return optGroup;
}

/**
 * 当支出收入单选框变化时，切换下拉框
 * @param flag 标识符
 */
const inComeChange = (flag) => {
    if (flag === 1) paymentTypeSel.innerHTML = paymentTypeInOp
    else paymentTypeSel.innerHTML = paymentTypeOutOp
}

const getAll = () => {
    const stuName = $("searchInput").value;
    const pageSize = paSize.value;
    if (stuName == null || stuName === '') {
        fetch(`/paymentRecord?pageNum=${nowPage}&pageSize=${pageSize}`)
            .then(response => response.json())
            .then(resp => {
                firstLoadPa(resp["total"], resp["pages"])
                addTableRow(resp["records"])
            })
            .catch((error) => console.error('Error:', error));
    }
};
// 传来的json变成表格
const addTableRow = (myStu) => {
    const tbMain = $("tb-main")
    tbMain.innerHTML = ''
    for (let i = 0; i < myStu.length; i++) { //遍历一下json数据
        const trow = getDataRow(myStu[i]); //定义一个方法,返回tr数据
        tbMain.appendChild(trow);
    }
};
const getDataRow = (h) => {
    const {recordId, keyName, isIncome, money, remark, payDate} = h;
    // 创建行
    let newRow = document.createElement('tr');
    newRow.innerHTML = `<td>${keyName}</td>
    <td><span class="${isIncome ? 'in' : 'out'}">${isIncome ? '+' : '-'}${money}</span></td>
    <td>${remark}</td>
    <td>${payDate}</td>
    <td><button class="del-btn" type="button"><img src="svg/trash.svg" alt="del"></button>
        <button class="upd-btn" type="button"><img src="svg/pencil-square.svg" alt="edit"></button></td>`;
    // 按钮事件
    newRow.querySelector('.del-btn').addEventListener('click', () => deleteById(recordId));
    newRow.querySelector('.upd-btn').addEventListener('click', () => jsonToForm(h));

    return newRow;
};
// 通过id删除单个
const deleteById = async (id) => {
    if (await confirmDel()) {
        fetch("/paymentRecord/" + id, {method: 'DELETE'})
            .then(resp => resp.json())
            .then((resp) => commonResp(resp));
    }
}

// 添加修改模态框
const dialog = $("myDialog");
const showDialog = () => {
    setPopTitle()
    dialog.showModal();
};
const closeDialog = () => {
    dialog.close()
};


// 设置表单标题(添加)
const setPopTitle = () => {
    $('recordId').value = ''
    const legend = $('dialogTit');
    legend.innerText = "添加记录"
};

// 提交表单信息
const submitForm = () => {
    dialog.close()
    if ($('dialogTit').innerText === "修改记录") {
        fetch('/paymentRecord', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formToJson()),
        })
            .then(resp => resp.json())
            .then((resp) => commonResp(resp));
    } else {
        fetch('/paymentRecord', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formToJson()),
        })
            .then(resp => resp.json())
            .then((resp) => commonResp(resp));
    }
};

//表单数据转json
const formToJson = () => {
    return {
        "recordId": $('recordId').value,
        "paymentType": $('paymentType').value,
        "isIncome": $("isIncome_1").checked ? 1 : 0,
        "money": $('money').value,
        "payDate": $('payDate').value,
        "remark": $('remark').value
    };
};

//json数据转表单
const jsonToForm = (h) => {
    const {recordId, paymentType, isIncome, money, payDate, remark} = h;
    $('recordId').value = recordId;
    $('paymentType').value = paymentType;
    if (isIncome === 1)
        $("isIncome_1").checked = true;
    else
        $("isIncome_2").checked = true;
    $('money').value = money;
    $('remark').value = remark;
    $('payDate').value = payDate;
    // 展示模态框
    $('dialogTit').innerText = "修改记录"
    dialog.showModal();
};

// 删改增操作通用解析
const commonResp = (resp) => {
    if (resp["code"] === 1) {
        showTus(resp["msg"])
        getAll();
    } else if (resp["code"] === 0) {
        showTus2(resp["msg"])
    } else {
        showTus2("服务器无响应")
    }
}

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
    const paOneList = pageStrip.getElementsByTagName("span");
    paOneList[nowPage - 1].classList.remove("active")
    paOneList[paNum - 1].classList.add("active")
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