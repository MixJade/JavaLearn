# ================================================================================
# 上传题源图片 模块
# ================================================================================

# 题源分类表 即图片文件夹
create table source_category
(
    category_id   int auto_increment comment '题源分类主键'
        primary key,
    category_name varchar(20) not null comment '分类名称',
    folder_name   varchar(20) not null comment '文件夹名称',
    remark        varchar(50) comment '备注',
    create_date date comment '创建日期'
) comment '题源分类表';

# 题源图片表 即图片文件夹下的图片
create table source_image
(
    image_id    int auto_increment comment '图片主键'
        primary key,
    file_name   varchar(30) not null comment '文件名称',
    category_id int         not null comment '题源分类主键',
    remark      varchar(50) comment '备注',
    ocr_result  text comment '文字识别结果',
    ocr_time    datetime DEFAULT NULL COMMENT '识别时间'
) comment '题源图片表';

# 为'题源图片表'关联外键
ALTER TABLE source_image
    ADD CONSTRAINT fk_image_category
        FOREIGN KEY (category_id) REFERENCES source_category (category_id)
            ON DELETE RESTRICT;

# ================================================================================
# 题目编辑 模块
# ================================================================================

# 科目表 考试科目，如：软件设计师
create table exam_subject
(
    subject_id      int auto_increment comment '科目主键'
        primary key,
    subject_name    varchar(20)                  not null comment '科目名称',
    exam_start_date date comment '正式考试日期',
    folder_name     varchar(20)                  not null comment '文件夹名称',
    create_date     date comment '创建日期'
) comment '科目表';

# 试卷表 科目下的试卷
create table exam_paper
(
    paper_id    int auto_increment comment '试卷主键'
        primary key,
    subject_id  int         not null comment '科目主键',
    paper_name  varchar(20) not null comment '试卷名称',
    folder_name varchar(20) not null comment '文件夹名称',
    total_score int         not null default 0 comment '总分(自动计算)',
    duration    int         not null default 0 comment '考试时长(秒)',
    create_date date comment '创建日期'
) comment '试卷表';

# 为'试卷表'关联外键
ALTER TABLE exam_paper
    ADD CONSTRAINT fk_paper_subject
        FOREIGN KEY (subject_id) REFERENCES exam_subject (subject_id)
            ON DELETE RESTRICT;

# 题目表 试卷的题目和解析
create table exam_quest
(
    quest_id       int auto_increment comment '题目主键'
        primary key,
    paper_id       int         not null comment '试卷主键',
    quest_type     int         not null default 1 comment '题目类型,1选择,2填空,3大题',
    quest_content  text        not null comment '题目内容',
    quest_analysis text comment '题目解析',
    quest_no       int         not null default 1 comment '题目序号',
    have_img       tinyint(1)  not null default 0 comment '存在图片',
    img_name       varchar(30) null     default 0 comment '图片名称',
    score          int         not null default 0 comment '分值'
) comment '题目表';

# 为'题目表'关联外键
ALTER TABLE exam_quest
    ADD CONSTRAINT fk_quest_paper
        FOREIGN KEY (paper_id) REFERENCES exam_paper (paper_id)
            ON DELETE RESTRICT;

# 题目选项表 题目的选项
create table exam_quest_opt
(
    opt_id     int auto_increment comment '选项主键'
        primary key,
    quest_id   int         not null comment '题目主键',
    opt_cont   text        not null comment '选项内容',
    have_img   tinyint(1)  not null default 0 comment '存在图片',
    img_name   varchar(30) null     default 0 comment '图片名称',
    is_correct tinyint(1)  not null default 0 comment '是否正确选项',
    opt_no     int         not null default 1 comment '选项排序(1,2,3)',
    opt_name   varchar(1)  not null comment '选项名称(A,B,C)'
) comment '题目选项表';

# 为'题目选项表'关联外键
ALTER TABLE exam_quest_opt
    ADD CONSTRAINT fk_opt_quest
        FOREIGN KEY (quest_id) REFERENCES exam_quest (quest_id)
            ON DELETE RESTRICT;


# ================================================================================
# 做题记录 模块
# ================================================================================

# 试卷记录表 记录做过的试卷
create table record_paper
(
    record_id    int auto_increment comment '记录主键'
        primary key,
    paper_id     int        not null comment '试卷主键',
    finish_score int comment '最终得分',
    is_finish    tinyint(1) not null default 0 comment '是否完成',
    start_time   datetime   not null comment '开始时间',
    finish_time  datetime            DEFAULT NULL COMMENT '完成时间'
) comment '试卷记录表';

# 题目记录表 记录做过的题目
create table record_quest
(
    quest_record_id int auto_increment comment '题目记录主键'
        primary key,
    record_id       int not null comment '试卷记录主键',
    quest_id        int not null comment '题目主键',
    quest_type      int not null comment '题目类型,1选择,2填空,3大题',
    quest_no        int not null comment '题目序号',
    op_name         varchar(50) comment '选项(A,B,略,填空)',
    correct_op_name varchar(50) comment '正确选项',
    score           int not null default 0 comment '得分'
) comment '题目记录表';