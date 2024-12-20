(() => {
    document.body.insertAdjacentHTML('beforeend', `<!--删除确认框-->
<dialog id="sureDelModal">
    <p>
        一旦删除，数据无法找回，是否删除
    </p>
    <div style="text-align: right">
        <button class="my-btn btn-danger" type="button" id="sureDelBtn">确认</button>
        <button class="my-btn btn-secondary" type="button" onclick="cancelDel()">取消</button>
    </div>
</dialog>
<!--吐司消息显示框-->
<div id="myTus"></div>
<!--吐司：正确消息-->
<template id="tus-temp">
    <div class="tus tus-suc">
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24">
            <circle cx="12" cy="12" r="11" fill="#67C23A"/>
            <path d="M7 12 L12 17 L17 7" stroke="white" stroke-width="2" fill="none"/>
        </svg>
        <span>这是一条吐司消息</span>
    </div>
</template>
<!--吐司：错误消息-->
<template id="tus-temp2">
    <div class="tus tus-fail">
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24">
            <circle cx="12" cy="12" r="11" fill="#F56C6C"/>
            <line x1="17" y1="7" x2="7" y2="17" stroke="white" stroke-width="2"/>
            <line x1="17" y1="17" x2="7" y2="7" stroke="white" stroke-width="2"/>
        </svg>
        <span>这是一条吐司消息</span>
    </div>
</template>`);
})();

const $ = id => document.getElementById(id);

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