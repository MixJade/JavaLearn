window.onload = () => {
    getAll()
};
const $ = (id) => {
    return document.getElementById(id);
}


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
        console.log(newTd.length)
        const {recordId, paymentType, isIncome, money, remark, payDate} = h;
        /*=====向模板中写入内容=====*/
        newTd[0].textContent = paymentType;
        newTd[1].textContent = isIncome ? "收入" : "支出";
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


const sureDelModal = $("sureDelModal"); // 确认删除模态框
const sureDelBtn = $("sureDelBtn"); // 确认删除按钮
const confirmDel = () => {
    sureDelModal.showModal();
    return new Promise((resolve) => {
        sureDelModal.addEventListener('close', () => {
            resolve(false);
        });
        sureDelBtn.onclick = () => {
            resolve(true);
            sureDelModal.close()
        }
    })
};
const cancelDel = () => {
    sureDelModal.close()
};

// 吐司消息
const showTus = (text) => {
    if ('content' in document.createElement('template')) {
        let t = $('tus-temp'),
            tSpan = t.content.querySelectorAll('span');
        let myTus = $('myTus');
        tSpan[0].textContent = text;
        // 克隆新行并插入
        let clone = document.importNode(t.content, true);
        myTus.appendChild(clone);
        // 设定4秒后删除这个元素
        setTimeout(() => {
            myTus.removeChild(myTus.firstElementChild);
        }, 4000);
    }
};
const showTus2 = (text) => {
    if ('content' in document.createElement('template')) {
        let t = $('tus-temp2'),
            tSpan = t.content.querySelectorAll('span');
        let myTus = $('myTus');
        tSpan[0].textContent = text;
        // 克隆新行并插入
        let clone = document.importNode(t.content, true);
        myTus.appendChild(clone);
        // 设定4秒后删除这个元素
        setTimeout(() => {
            myTus.removeChild(myTus.firstElementChild);
        }, 4000);
    }
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