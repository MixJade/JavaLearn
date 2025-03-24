window.onload = () => {
    getBigType()
    getAll()
};
const bigTypeMap = new Map();
const bigTypeColorMap = new Map();
const bigTypeSearch = $("bigTypeSearch");
const getBigType = () => {
    bigTypeSearch.innerHTML = `<option value=""> </option>`
    fetch('/api/paymentDict/bigType')
        .then(response => response.json())
        .then(resp => {
            const bigType = $('bigType')
            for (let respElement of resp) {
                const {typeKey, typeName, color} = respElement
                bigTypeMap.set(typeKey, typeName)
                bigTypeColorMap.set(typeKey, color)
                bigType.innerHTML += `<option value="${typeKey}">${typeName}</option>`
                bigTypeSearch.innerHTML += `<option value="${typeKey}">${typeName}</option>`
            }
        })
        .catch((error) => console.error('Error:', error));
}

const getAll = () => {
    fetch('/api/paymentDict?bigType=' + bigTypeSearch.value)
        .then(response => response.json())
        .then(resp => addTableRow(resp))
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
    const {paymentType, keyName, isIncome, bigType, color, recordNum} = h;
    // 创建行
    let newRow = document.createElement('tr');
    newRow.innerHTML = `
    <td>${isIncome ? '<span class="in">收入</span>' : '<span class="out">支出</span>'}</td>
    <td style="color: ${bigTypeColorMap.get(bigType)};font-weight: bolder">${bigTypeMap.get(bigType)}</td>
    <td style="color: ${color};font-weight: bolder">${keyName}</td>
    <td>${color}</td>
    <td>${recordNum}</td>
    <td><button class="del-btn" type="button"><img src="svg/trash.svg" alt="del"></button>
        <button class="upd-btn" type="button"><img src="svg/pencil-square.svg" alt="edit"></button></td>`;
    // 按钮事件
    newRow.querySelector('.del-btn').addEventListener('click', () => deleteById(paymentType, recordNum));
    newRow.querySelector('.upd-btn').addEventListener('click', () => jsonToForm(h));

    return newRow;
};
// 通过id删除单个
const deleteById = async (id, recordNum) => {
    if (recordNum > 0) {
        showTus2("数量大于0,不可删除")
        return;
    }
    if (await confirmDel()) {
        fetch("/api/paymentDict/" + id, {method: 'DELETE'})
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
    $('paymentType').value = ''
    const legend = $('dialogTit');
    legend.innerText = "添加字典"
};

// 提交表单信息
const submitForm = () => {
    dialog.close()
    if ($('dialogTit').innerText === "修改字典") {
        fetch('/api/paymentDict', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formToJson()),
        })
            .then(resp => resp.json())
            .then((resp) => commonResp(resp));
    } else {
        fetch('/api/paymentDict', {
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
        "paymentType": $('paymentType').value,
        "keyName": $('keyName').value,
        "isIncome": $("isIncome_1").checked ? 1 : 0,
        "bigType": $('bigType').value,
        "color": $('color').value
    };
};

//json数据转表单
const jsonToForm = (h) => {
    const {paymentType, keyName, isIncome, bigType, color} = h;
    $('paymentType').value = paymentType;
    $('keyName').value = keyName;
    if (isIncome === 1)
        $("isIncome_1").checked = true;
    else
        $("isIncome_2").checked = true;
    $('bigType').value = bigType;
    $('color').value = color;
    // 展示模态框
    $('dialogTit').innerText = "修改字典"
    dialog.showModal();
};

// 选择大类时，改变字典颜色
const changeBigTypeColor = () => {
    const colorTxt = bigTypeColorMap.get(Number($('bigType').value));
    const bigTypeColor = $('bigTypeColor')
    bigTypeColor.innerText = colorTxt;
    bigTypeColor.style.color = colorTxt;
    $('color').value = colorTxt;
}

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