window.onload = () => {
    getPayType()
    getBigType()
    // 看是否有页面传参
    const queryParam = getQueryParams()["month"];
    if (queryParam !== undefined) getStartAndEndOfMonth(queryParam)
    getAll();
};

/**
 * 设置一月的第一天和最后一天
 *
 * @param monthStr 形如 2024-1 或 2024-1-1
 */
const getStartAndEndOfMonth = (monthStr) => {
    const dateStr = monthStr.split("-");
    if (dateStr.length === 1) {
        // 只有年
        // 本年第一天
        const startDateV = new Date(dateStr[0], 0, 1);
        startDateV.setHours(8) //东八时区
        // 本年最后一天
        const endDateV = new Date(dateStr[0],11, 31);
        endDateV.setHours(8) //东八时区
        // 设值
        beginDate.value = startDateV.toISOString().slice(0, 10);
        endDate.value = endDateV.toISOString().slice(0, 10);
    } else if (dateStr.length === 2) {
        // 只有年+月
        // 本月第一天
        const startDateV = new Date(dateStr[0], dateStr[1] - 1, 1);
        startDateV.setHours(8) //东八时区
        // 本月最后一天
        const endDateV = new Date(dateStr[0], dateStr[1], 0);
        endDateV.setHours(8) //东八时区
        // 设值
        beginDate.value = startDateV.toISOString().slice(0, 10);
        endDate.value = endDateV.toISOString().slice(0, 10);
    } else {
        // 有 年+月+日
        const clickDate = `${dateStr[0]}-${String(dateStr[1]).padStart(2, '0')}-${String(dateStr[2]).padStart(2, '0')}`
        beginDate.value = clickDate;
        endDate.value = clickDate;
    }
}

/**
 * 组装支付类型下拉框
 */
let paymentTypeInOp = ""; // 收入列表
let paymentTypeOutOp = ""; // 支出列表
const paymentTypeSel = $('paymentType')
const getPayType = () => {
    fetch('/api/paymentDict/option')
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
const bigTypeSearch = $("bigTypeSearch");
const paymentTypeSearch = $("paymentTypeSearch");
const smallPayMay = new Map(); // 大类下的小类映射
const getBigType = () => {
    bigTypeSearch.innerHTML = `<option value=""> </option>`
    fetch('/api/paymentDict/bigType')
        .then(response => response.json())
        .then(resp => {
            for (let respElement of resp) {
                const {typeKey, typeName} = respElement
                bigTypeSearch.innerHTML += `<option value="${typeKey}">${typeName}</option>`
            }
        })
        .catch((error) => console.error('Error:', error));
    // 旁边的小分类
    paymentTypeSearch.innerHTML = '<option value=""> </option>';
}
// 搜索框的大类变化
const bigTypeChange = () => {
    const bigType = bigTypeSearch.value;
    if (bigType === "") return;
    paymentTypeSearch.value = "";
    const paymentDictList = smallPayMay.get(Number(bigType))
    paymentTypeSearch.innerHTML = '<option value=""> </option>';
    for (const paymentDict of paymentDictList) {
        const {paymentType, keyName} = paymentDict
        paymentTypeSearch.innerHTML += `<option value="${paymentType}">${keyName}</option>`
    }
    getAll();
}
// 组装表单中的下拉框，顺便组装查询表单所需的map
const getOpGroup = (respElement) => {
    const {bigType, typeName, paymentDictList} = respElement
    // 放入map
    if (smallPayMay.has(bigType)) {
        // 如果键已经存在，将新列表与原列表合并
        const existingList = smallPayMay.get(bigType);
        const mergedList = existingList.concat(paymentDictList);
        smallPayMay.set(bigType, mergedList);
    } else {
        // 如果键不存在，将键和列表作为新的键值对插入到 Map 中
        smallPayMay.set(bigType, paymentDictList);
    }
    // 组装表单
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

const beginDate = $("beginDate")
const endDate = $("endDate")
const getAll = () => {
    const bigTypeVal = bigTypeSearch.value;
    const paymentTypeVal = paymentTypeSearch.value;
    const beginDateVal = beginDate.value;
    const endDateVal = endDate.value;
    const pageSize = paSize.value;
    // 拼接参数
    fetch(`/api/paymentRecord?pageNum=${nowPage}&pageSize=${pageSize}&bigType=${bigTypeVal}&paymentType=${paymentTypeVal}&beginDate=${beginDateVal}&endDate=${endDateVal}`)
        .then(response => response.json())
        .then(resp => {
            firstLoadPa(resp["total"], resp["pages"])
            addTableRow(resp["records"])
        })
        .catch((error) => console.error('Error:', error));
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
    const {recordId, keyName, color, isIncome, money, remark, payDate} = h;
    // 创建行
    let newRow = document.createElement('tr');
    newRow.innerHTML = `<td style="color: ${color};font-weight: bolder">${keyName}</td>
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
        fetch("/api/paymentRecord/" + id, {method: 'DELETE'})
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

const recordIdInput = $('recordId');
const remarkInput = $('remark');
// 设置表单标题(添加)
const setPopTitle = () => {
    recordIdInput.value = ''
    remarkInput.value = ''
    const legend = $('dialogTit');
    legend.innerText = "添加记录"
};

// 提交表单信息
const submitForm = () => {
    dialog.close()
    if ($('dialogTit').innerText === "修改记录") {
        fetch('/api/paymentRecord', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formToJson()),
        })
            .then(resp => resp.json())
            .then((resp) => commonResp(resp));
    } else {
        fetch('/api/paymentRecord', {
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

const payDateInput = $('payDate')
//表单数据转json
const formToJson = () => {
    return {
        "recordId": recordIdInput.value,
        "paymentType": $('paymentType').value,
        "isIncome": $("isIncome_1").checked ? 1 : 0,
        "money": $('money').value,
        "payDate": payDateInput.value,
        "remark": remarkInput.value
    };
};

//json数据转表单
const jsonToForm = (h) => {
    const {recordId, paymentType, isIncome, money, payDate, remark} = h;
    recordIdInput.value = recordId;
    $('paymentType').value = paymentType;
    if (isIncome === 1)
        $("isIncome_1").checked = true;
    else
        $("isIncome_2").checked = true;
    $('money').value = money;
    remarkInput.value = remark;
    payDateInput.value = payDate;
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

// 添加时日期加一天
const dateAddWhenAdd = () => {
    if (payDateInput.value === '') return;
    const date = new Date(payDateInput.value);
    date.setDate(date.getDate() + 1);
    payDateInput.value = date.toISOString().slice(0, 10);
}
