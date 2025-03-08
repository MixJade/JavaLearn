window.onload = () => {
    getAll()
};
const ele = (id) => {
    return document.getElementById(id);
}


const getAll = () => {
    const stuName = ele("searchInput").value;
    if (stuName == null || stuName === '') {
        fetch('http://localhost:8080/students')
            .then(response => response.json())
            .then(resp => addTableRow(resp))
            .catch((error) => console.error('Error:', error));
    } else {
        fetch("http://localhost:8080/students/like/" + stuName)
            .then(response => response.json())
            .then(resp => addTableRow(resp))
            .catch((error) => console.error('Error:', error));
    }
};
// 传来的json变成表格
const addTableRow = (myStu) => {
    const tbMain = ele("tb-main")
    tbMain.innerHTML = ''
    for (let i = 0; i < myStu.length; i++) { //遍历一下json数据
        const trow = getDataRow(myStu[i]); //定义一个方法,返回tr数据
        tbMain.appendChild(trow);
    }
};
const t = ele("trTemp");
const getDataRow = (h) => {
    if ('content' in document.createElement('template')) {
        let newTd = t.content.querySelectorAll("td");
        const {studentName, birthday, id, sex, englishGrade, mathGrade, societyId, money, height} = h;
        /*=====向模板中写入内容=====*/
        const aCheck = newTd[0].querySelector("input[type=checkbox]");
        aCheck.setAttribute('placeholder', id);
        newTd[1].textContent = studentName;
        newTd[2].textContent = getSocietyName(societyId);
        newTd[3].textContent = sex ? "男" : "女";
        newTd[4].textContent = englishGrade;
        newTd[5].textContent = mathGrade;
        newTd[6].textContent = getAge(birthday);
        newTd[7].textContent = money;
        newTd[8].textContent = height;
        // 克隆新行并设置事件
        let clone = document.importNode(t.content, true);
        // 删除事件
        const delBtn = clone.querySelectorAll("button.del-btn")[0];
        delBtn.addEventListener('click', () => deleteById(id));
        // 修改事件
        const updBtn = clone.querySelectorAll("button.upd-btn")[0];
        updBtn.addEventListener('click', () => jsonToForm(h));
        return clone;
    }
};
// 通过id删除单个
const deleteById = async (id) => {
    if (await confirmDel()) {
        fetch("http://localhost:8080/students/" + id, {method: 'DELETE'})
            .then(resp => resp.json())
            .then((resp) => commonResp(resp));
    }
}
const getSocietyName = (societyId) => {
    switch (societyId) {
        case 1:
            return "散人"
        case 2:
            return "头文字D"
        case 3:
            return "轻音部"
        case 4:
            return "黄金之风"
        case 5:
            return "极东魔术昼寝结社"
        default:
            return "未知"
    }
};

// 根据出生日期算年龄(简化版)
const nowYear = new Date().getFullYear();
const getAge = (birthday) => {
    const birthYear = birthday.split("-")[0]
    return nowYear - birthYear
};

// 添加修改模态框
const dialog = ele("myDialog");
const showDialog = () => {
    setPopTitle()
    dialog.showModal();
};
const closeDialog = () => {
    dialog.close()
};

// 删除多个
const deleteBatch = async () => {
    if (await confirmDel()) {
        const delGroup = [];
        for (let i = 0; i < checkAS.length; i++) {
            if (checkAS[i].checked) {
                delGroup.push(checkAS[i].placeholder)
            }
        }
        if (delGroup.length === 0) {
            return
        }
        fetch("http://localhost:8080/students/batch/" + delGroup, {method: 'DELETE'})
            .then(resp => resp.json())
            .then((resp) => commonResp(resp));
    } else {
        showTus2("取消删除")
    }
}
const sureDelModal = ele("sureDelModal"); // 确认删除模态框
const sureDelBtn = ele("sureDelBtn"); // 确认删除按钮
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
        let t = ele('tus-temp'),
            tSpan = t.content.querySelectorAll('span');
        let myTus = ele('myTus');
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
        let t = ele('tus-temp2'),
            tSpan = t.content.querySelectorAll('span');
        let myTus = ele('myTus');
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

// 多选与反选
const checkAS = document.getElementsByName("checkA");
const checkAll = (e) => {
    for (let i = 0; i < checkAS.length; i++) {
        checkAS[i].checked = e.checked;
    }
};

// 设置表单标题(添加)
const setPopTitle = () => {
    const addPetForm = ele('addPetForm');
    addPetForm.reset();
    ele('id').value = ''
    const legend = ele('dialogTit');
    legend.innerText = "添加学生"
};

// 提交表单信息
const submitForm = () => {
    dialog.close()
    if (ele('dialogTit').innerText === "修改学生") {
        fetch('http://localhost:8080/students', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formToJson()),
        })
            .then(resp => resp.json())
            .then((resp) => commonResp(resp));
    } else {
        fetch('http://localhost:8080/students', {
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
        "studentName": ele('studentName').value,
        "birthday": ele('birthday').value,
        "id": ele('id').value,
        "sex": ele("petSexRad_1").checked ? 1 : 0,
        "englishGrade": ele('englishGrade').value,
        "mathGrade": ele('mathGrade').value,
        "societyId": ele("societyId").value,
        "money": ele("money").value,
        "height": ele("height").value
    };
};

//json数据转表单
const jsonToForm = (h) => {
    const {studentName, birthday, id, sex, englishGrade, mathGrade, societyId, money, height} = h
    ele('studentName').value = studentName;
    ele('birthday').value = birthday;
    ele('id').value = id;
    if (sex === 1) {
        ele("petSexRad_1").checked = true;
    } else {
        ele("petSexRad_2").checked = true;
    }
    ele('englishGrade').value = englishGrade;
    ele('mathGrade').value = mathGrade;
    ele("societyId").value = societyId;
    ele("money").value = money;
    ele("height").value = height;
    // 展示模态框
    ele('dialogTit').innerText = "修改学生"
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