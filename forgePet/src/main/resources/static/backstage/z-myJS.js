// 简化表格与表单
class MixRole {
    constructor(role, roleName) {
        this._role = role;
        this._roleName = roleName
        document.getElementById("submitForm").onclick = () => this.upAndAdd()
    };// 一个参数的分页查询
    getAll(numPage) {
        const search1 = this._role + "Name"
        const json = {"numPage": numPage, "pageSize": this.getPageSize()}
        json[search1] = document.getElementById("searchInput").value;
        axios.get("http://localhost:8080/" + this._role + "/page", {
            params: json
        }).then(resp => {
            this.addTableRow(resp.data["data"]);
            this.pageStripWrite(resp.data["len"], numPage)
        })
    }// 表格主体
    tbody = document.getElementById('tb-main');//传来的json变成表格
    addTableRow(json) {
        this.tbody.innerHTML = ''
        for (let i = 0; i < json.length; i++) { //遍历一下json数据
            const trow = getDataRow(json[i]); //定义一个方法,返回tr数据
            this.tbody.appendChild(trow);
        }
    }//删除与修改按钮
    opCell(roleId, h) {
        const role = this._role;
        const delCell = document.createElement('td');
        const btnDel = document.createElement('input');
        btnDel.setAttribute('type', 'button');
        btnDel.setAttribute('class', 'btn btn-warning ms-1');
        btnDel.setAttribute('value', '删除');
        delCell.appendChild(btnDel);
        const btnUpd = document.createElement('input');
        btnUpd.setAttribute('type', 'button');
        btnUpd.setAttribute('class', 'btn btn-info ms-1');
        btnUpd.setAttribute("data-bs-toggle", "modal");
        btnUpd.setAttribute("data-bs-target", "#addRolePop");
        btnUpd.setAttribute('value', '修改');
        delCell.appendChild(btnUpd);
        //删除操作
        btnDel.onclick = async () => {
            if (await confirmDel()) {
                axios.delete("http://localhost:8080/" + role + "/" + roleId).then(resp => this.commonResp(resp))
            }
        }// 修改操作
        btnUpd.onclick = () => this.upTit(h)
        return delCell
    }//创建行
    cell(key) {
        const jsCell = document.createElement('td')
        jsCell.innerHTML = key
        return jsCell
    }//布尔操作
    sexCell(bool, sexT, sexF) {
        if (bool) {
            return this.cell(sexT)
        } else {
            return this.cell(sexF)
        }
    }// 表格图片
    img(rolePhoto) {
        if (rolePhoto != null) {
            return '<img src="http://localhost:8080/common/download?name=' + rolePhoto + '" height="45px" width="45px" alt="' + rolePhoto + '"/>'
        } else {
            return ''
        }
    }// 添加复选框
    check(roleId) {
        const checkRow = document.createElement('td');
        const checkA = document.createElement('input');
        checkA.type = 'checkbox';
        checkA.className = 'form-check-input ms-2';
        checkA.setAttribute('name', 'checkA');
        checkA.setAttribute('placeholder', roleId);
        checkRow.appendChild(checkA);
        return checkRow
    }// 根据出生日期算年龄(简化版)
    nowYear = new Date().getFullYear()//获取年数差
    age(birthday) {
        const birthYear = birthday.split("-")[0]
        return this.nowYear - birthYear
    }//批量删除
    deleteGroup(checkAS) {
        const delGroup = [];
        for (let i = 0; i < checkAS.length; i++) {
            if (checkAS[i].checked) {
                delGroup.push(checkAS[i].placeholder)
            }
        }
        if (delGroup.length === 0) {
            return
        }
        axios.delete("http://localhost:8080/" + this._role + "/batch/" + delGroup).then(resp => this.commonResp(resp))
    }//修改与添加
    upAndAdd() {
        if (document.getElementById('addRolePopLa').innerHTML === "修改" + this._roleName + "信息") {
            axios.put("http://localhost:8080/" + this._role, formToJson()).then(resp => this.commonResp(resp))
        } else {
            axios.post("http://localhost:8080/" + this._role, formToJson()).then(resp => this.commonResp(resp))
        }
    }//设置添加弹出框标题
    addTit() {
        const form = document.getElementById('addRoleForm')
        form.reset();
        const hidIds = form.querySelectorAll('input[type="hidden"]')
        for (let i = 0; i < hidIds.length; i++) {
            hidIds[i].value = ""
        }
        document.getElementById('addRolePopLa').innerHTML = "添加" + this._roleName
    }//设置修改弹出框标题
    upTit(h) {
        document.getElementById('addRolePopLa').innerHTML = "修改" + this._roleName + "信息";
        jsonToForm(h)
    }//json转表单(只限value操作)
    setVal(group, h) {
        try {
            for (let i = 0; i < group.length; i++) {
                document.getElementById(group[i]).value = h[group[i]]
            }
        } catch (e) {
            console.log("setVal:有value不符合标准")
        }
    }//json的性别转表单(只限checked操作)
    setSex(sex, field) {
        if (sex) {
            document.getElementById(field + "_1").checked = true
        } else {
            document.getElementById(field + "_2").checked = true
        }
    }//表单转json(只限value操作)
    getVal(group) {
        try {
            const json = {};
            for (let i = 0; i < group.length; i++) {
                json[group[i]] = document.getElementById(group[i]).value
            }
            return json
        } catch (e) {
            console.log("getVal:有value不符合标准")
        }
    }// 删改增操作通用解析
    commonResp(resp) {
        const currentPage = this.pageStrip.querySelector(".active").getElementsByTagName("a")[0].innerHTML;
        if (resp.data["code"] === 1) {
            sucToast(resp.data["msg"])
            this.getAll(currentPage);
        } else if (resp.data["code"] === 0) {
            errToast(resp.data["msg"])
        } else {
            errToast("服务器无响应")
        }
    }

    pageStrip = document.getElementById("pageStrip");// 分页条
    getPageSize() {
        return document.getElementById("pageSize").value
    }// 分页条向前向后翻
    addPageNum(isAdd) {
        const currentPage = parseInt(this.pageStrip.querySelector(".active").getElementsByTagName("a")[0].innerHTML);
        if (isAdd && currentPage !== 1) {
            this.getAll(currentPage - 1)
        } else if (!isAdd && currentPage !== this.pageStrip.getElementsByTagName("li").length) {
            this.getAll(currentPage + 1)
        }
    }// 分页条设置长度与选中
    pageStripWrite(dataLen, numPage) {
        const pageNum = dataLen / (this.getPageSize());
        this.pageStrip.innerHTML = ''
        for (let i = 1; i < pageNum + 1; i++) {
            this.pageStrip.innerHTML += '<li class="page-item"><a class="page-link" onclick="sss.getAll(' + i + ')">' + i + '</a></li>'
        }
        try {
            this.pageStrip.getElementsByTagName("a")[numPage - 1].parentNode.classList.add("active");
        } catch (e) {
            console.log("页数过少")
        }
    }
}

// 图片上传与回显
class MixImg {
    constructor() {
        this.myFile = document.getElementById("myFile")
        this.myFile.onchange = () => this.imgChange()
        this._myImg = document.getElementById("rolePhoto");
        this.myFileText = document.getElementById("myFileText")
    }// 获取图片名
    get myImg() {
        return this._myImg.alt;
    }// 上传框变化时，检查图片大小
    imgChange() {
        const myFileText = this.myFileText.style
        const myFile = this.myFile.files[0]
        if (myFile.size > 1024000) {
            myFileText.display = "";
        } else {
            myFileText.display = "none";
            const formData = new FormData();
            formData.append("myFile", myFile)
            axios.post("http://localhost:8080/common/upload", formData).then(resp => {
                this.change(resp.data)
            })
        }
    }// 改变表单图片
    change(imgName) {
        const myImg = this._myImg
        const myFileText = this.myFileText.style
        if (imgName === '' || imgName === null) {
            myFileText.display = "none";
            myImg.setAttribute("src", '../picture/none.png')
            myImg.setAttribute("alt", '')
        } else {
            myImg.setAttribute("src", 'http://localhost:8080/common/download?name=' + imgName)
            myImg.setAttribute("alt", imgName)
        }
    }
}

try {// 吐司消息
    const toast01 = window.parent.document.getElementById("toast01");// 成功消息
    const toast02 = window.parent.document.getElementById("toast02");// 失败消息
    function sucToast(msg) {
        toast01.querySelector(".toast-body").innerHTML = msg;
        new bootstrap.Toast(toast01).show();
    }// 失败消息
    function errToast(msg) {
        toast02.querySelector(".toast-body").innerHTML = msg;
        new bootstrap.Toast(toast02).show();
    }
} catch (e) {
    console.log("没有找到吐司消息")
}


try {// 确认删除框
    const delModalFirst = window.parent.document.getElementById("delModal")
    const sureDelBtn = window.parent.document.getElementById("sureDelBtn")//确认删除
    const delModal = new bootstrap.Modal(delModalFirst)

    function confirmDel() {
        delModal.show();
        return new Promise(function (resolve) {
            delModalFirst.addEventListener('hide.bs.modal', function () {
                resolve(false)
            })
            sureDelBtn.onclick = function () {
                resolve(true);
                delModal.hide();
            }
        })
    }
} catch (e) {
    console.log("没有找到确认删除框")
}


try {// 多选与反选
    const checkAS = document.getElementsByName("checkA");
    document.getElementById("checkALL").onclick = function () {
        for (let i = 0; i < checkAS.length; i++) {
            checkAS[i].checked = this.checked;
        }
    }// 批量删除
    document.getElementById("delALL").onclick = async function () {
        if (await confirmDel()) {
            sss.deleteGroup(checkAS)
        }
    }
} catch (e) {
    console.log("批量删除的一些元素不在")
}