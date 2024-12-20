window.onload = () => {
    getPayType()
    getAll()
};

const paymentTypeMap = new Map();
const getPayType = () => {
    fetch('/paymentDict/option')
        .then(response => response.json())
        .then(resp => {
            const paymentTypeSel = $('paymentType')
            for (let respElement of resp) {
                const {paymentType, keyName} = respElement
                paymentTypeMap.set(paymentType, keyName)
                paymentTypeSel.innerHTML += `<option value="${paymentType}">${keyName}</option>`
            }
        })
        .catch((error) => console.error('Error:', error));
};
const getAll = () => {
    const stuName = $("searchInput").value;
    if (stuName == null || stuName === '') {
        fetch('/paymentRecord')
            .then(response => response.json())
            .then(resp => addTableRow(resp))
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
const t = $("trTemp");
const getDataRow = (h) => {
    if ('content' in document.createElement('template')) {
        let newTd = t.content.querySelectorAll("td");
        const {recordId, paymentType, isIncome, money, remark, payDate} = h;
        /*=====向模板中写入内容=====*/
        newTd[0].textContent = isIncome ? "收入" : "支出";
        newTd[1].textContent = paymentTypeMap.get(paymentType);
        newTd[2].textContent = money;
        newTd[3].textContent = remark;
        newTd[4].textContent = payDate;
        // 克隆新行并设置事件
        let clone = document.importNode(t.content, true);
        // 删除事件
        const delBtn = clone.querySelectorAll("button.del-btn")[0];
        delBtn.addEventListener('click', () => deleteById(recordId));
        // 修改事件
        const updBtn = clone.querySelectorAll("button.upd-btn")[0];
        updBtn.addEventListener('click', () => jsonToForm(h));
        return clone;
    }
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
    const addPetForm = $('addPetForm');
    addPetForm.reset();
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