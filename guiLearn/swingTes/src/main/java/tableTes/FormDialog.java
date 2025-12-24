package tableTes;

import javax.swing.*;
import java.awt.*;

/**
 * 带初始值的表单弹窗
 */
public class FormDialog extends JDialog {
    // 表单组件
    private final JTextField nameField = new JTextField(20),
            ageField = new JTextField(20),
            emailField = new JTextField(20);
    // 存储表单结果
    private String[] formData;
    // 确认按钮状态标记
    private boolean confirmed = false;

    /**
     * 构造方法 - 接收初始值
     *
     * @param parent 父窗口
     * @param title  弹窗标题
     */
    public FormDialog(JFrame parent, String title) {
        // 初始化弹窗
        super(parent, title, true); // true表示模态弹窗（阻塞父窗口）
        setSize(400, 250);
        setLocationRelativeTo(parent); // 相对于父窗口居中
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // 设置布局
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // 组件间距
        gbc.anchor = GridBagConstraints.WEST;

        // 添加组件到弹窗
        // 姓名行
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("姓名:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        add(nameField, gbc);

        // 年龄行
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        add(new JLabel("年龄:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        add(ageField, gbc);

        // 性别行
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        add(new JLabel("性别:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        add(emailField, gbc);

        // 按钮行
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        JButton confirmBtn = new JButton("确认");
        JButton cancelBtn = new JButton("取消");

        // 确认按钮事件
        confirmBtn.addActionListener(e -> {
            // 收集表单数据
            formData = new String[]{
                    nameField.getText().trim(),
                    ageField.getText().trim(),
                    emailField.getText().trim()
            };
            confirmed = true;
            dispose(); // 关闭弹窗
        });

        // 取消按钮事件
        cancelBtn.addActionListener(e -> {
            confirmed = false;
            dispose(); // 关闭弹窗
        });

        buttonPanel.add(confirmBtn);
        buttonPanel.add(cancelBtn);
        add(buttonPanel, gbc);
    }

    /**
     * 初始化表单组件并填充初始值
     */
    public void setInitialData(MyData myData) {
        nameField.setText(myData.name());
        ageField.setText(myData.age());
        emailField.setText(myData.sex() ? "男" : "女");
    }

    /**
     * 获取表单数据
     *
     * @return 表单数据数组 [姓名, 年龄, 性别]
     */
    public String[] getFormData() {
        return formData;
    }

    /**
     * 判断用户是否点击了确认按钮
     *
     * @return true-确认，false-取消
     */
    public boolean isConfirmed() {
        return confirmed;
    }

    // 测试主方法
    public static void main(String[] args) {
        // 在事件调度线程中运行Swing组件
        SwingUtilities.invokeLater(() -> {
            // 创建主窗口
            JFrame mainFrame = new JFrame("主窗口");
            mainFrame.setSize(500, 400);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setLayout(new FlowLayout());

            // 测试按钮 - 打开表单弹窗
            JButton openDialogBtn = new JButton("打开表单弹窗");
            openDialogBtn.addActionListener(e -> {
                // 创建并显示弹窗
                FormDialog dialog = new FormDialog(mainFrame, "用户信息编辑");
                dialog.setInitialData(new MyData("张三", "25", true));
                dialog.setVisible(true);

                // 获取弹窗返回结果
                if (dialog.isConfirmed()) {
                    String[] result = dialog.getFormData();
                    System.out.println(
                            "你修改后的信息：\n" +
                                    "姓名：" + result[0] + "\n" +
                                    "年龄：" + result[1] + "\n" +
                                    "性别：" + result[2]
                    );
                }
            });
            mainFrame.setLocationRelativeTo(null); //此语句将窗口定位在屏幕的中央
            mainFrame.add(openDialogBtn);
            mainFrame.setVisible(true);
        });
    }
}