package someUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

/**
 * 日历GUI的尝试
 *
 * @since 2024-8-2 23:25:22
 */
public class CalendarTrain extends JFrame implements ActionListener {
    private final Integer[] YEAR_OPTION = {2022, 2023, 2024, 2025, 2026},
            MONTH_OPTION = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    //月份和年份下拉 列表框
    private final JComboBox<Integer> MONTH_BOX = new JComboBox<>(MONTH_OPTION),
            YEAR_BOX = new JComboBox<>(YEAR_OPTION);

    // 今天按钮
    private final JButton BUTTON_TODAY = new JButton("今天");


    // 获取今天的日期、年份和月份
    private LocalDate nowDate = LocalDate.now();

    private final int NOW_YEAR = nowDate.getYear(),
            NOW_MONTH = nowDate.getMonthValue();
    // 显示今天日期的Label
    private final JLabel nowDateLabel = new JLabel(nowDate.toString());

    //用按钮显示日期，一共6行7列
    private final JButton[] buttonDay = new JButton[6 * 7];

    /*构造函数*/
    public CalendarTrain() {
        super();
        this.setTitle("日历");
        init();
        this.setLocation(500, 300);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
    }

    /**
     * 初始化日历
     */
    private void init() {
        // 选中当前日期
        YEAR_BOX.setSelectedItem(NOW_YEAR);
        MONTH_BOX.setSelectedItem(NOW_MONTH);
        // 放置下拉列表框和控制按钮的面板
        JPanel panel_one = new JPanel();
        panel_one.add(new JLabel("年份："));
        panel_one.add(YEAR_BOX);
        panel_one.add(new JLabel("月份："));
        panel_one.add(MONTH_BOX);
        panel_one.add(BUTTON_TODAY);
        //为输入项添加时间监听器
        BUTTON_TODAY.addActionListener(this);
        YEAR_BOX.addActionListener(this);
        MONTH_BOX.addActionListener(this);

        JPanel panel_two = new JPanel();
        // 7*7 第一行是星期
        panel_two.setLayout(new GridLayout(7, 7, 3, 3));
        // 设置星期
        String[] week = {"日", "一", "二", "三", "四", "五", "六"};
        JLabel[] weekLabelArr = new JLabel[7];
        for (int i = 0; i < 7; i++) {
            weekLabelArr[i] = new JLabel(week[i], SwingConstants.CENTER);
            weekLabelArr[i].setForeground(Color.black);
            panel_two.add(weekLabelArr[i]);
        }
        weekLabelArr[0].setForeground(Color.GRAY);
        weekLabelArr[6].setForeground(Color.GRAY);
        // 放置42个日期按钮
        for (int i = 0; i < buttonDay.length; i++) {
            buttonDay[i] = new JButton("");
            buttonDay[i].addActionListener(this);
            panel_two.add(buttonDay[i]);
        }
        this.paintDay(); // 显示当前日期

        // 显示当前日期
        JPanel panel_three = new JPanel();
        panel_three.add(nowDateLabel);

        // 添加组件
        JPanel panel_main = new JPanel();
        panel_main.setLayout(new BorderLayout());
        panel_main.add(panel_one, BorderLayout.NORTH);
        panel_main.add(panel_two, BorderLayout.CENTER);
        panel_main.add(panel_three, BorderLayout.SOUTH);
        getContentPane().add(panel_main);
    }

    /**
     * 绘制天数按钮的值
     */
    private void paintDay() {
        // 获取选中天
        LocalDate local_date = LocalDate.of(
                YEAR_OPTION[YEAR_BOX.getSelectedIndex()],
                MONTH_OPTION[MONTH_BOX.getSelectedIndex()],
                1);
        // 获取当月第一天
        LocalDate firstDay = local_date.with(TemporalAdjusters.firstDayOfMonth());
        int day1Week = firstDay.getDayOfWeek().getValue();
        System.out.println("当月起始：" + firstDay + " 星期：" + day1Week);
        // 插入空白天
        for (int i = 0; i < day1Week; i++) {
            buttonDay[i].setVisible(false);
            buttonDay[i].setText("");
        }
        // 遍历每一天，看看哪些是周六，哪些是周日
        int lengthOfMonth = local_date.lengthOfMonth();
        for (int j = 0; j < lengthOfMonth; j++) {
            // 天数加一后的星期
            LocalDate plusDays = firstDay.plusDays(j);
            DayOfWeek dayOfWeek = plusDays.getDayOfWeek();
            buttonDay[j + day1Week].setVisible(true);
            buttonDay[j + day1Week].setText(String.valueOf(plusDays.getDayOfMonth()));
            if (plusDays.equals(nowDate)) {
                // 当天设为蓝色
                buttonDay[j + day1Week].setForeground(Color.BLUE);
            } else if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
                // 如果是周六或者周日，设为红色
                buttonDay[j + day1Week].setForeground(Color.GRAY);
            } else {
                buttonDay[j + day1Week].setForeground(Color.BLACK);
            }
        }
        // 当月剩下的天全部清空
        for (int k = day1Week + lengthOfMonth; k < buttonDay.length; k++) {
            buttonDay[k].setVisible(false);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == BUTTON_TODAY) {
            // 点击“今天”按钮
            nowDate = LocalDate.now();
            nowDateLabel.setText(nowDate.toString());
            YEAR_BOX.setSelectedItem(NOW_YEAR);
            MONTH_BOX.setSelectedItem(NOW_MONTH);
            paintDay();
        } else if ((e.getSource() == YEAR_BOX || e.getSource() == MONTH_BOX)) {
            // 下拉框变化
            paintDay();
        } else {
            // 点击日历中天数
            String command = e.getActionCommand();
            if (!"".equals(command)) {
                changeNowDate(command);
            }
        }
    }

    /**
     * 日历中的按钮事件,改变日历中显示的当前天数
     *
     * @param dayOfMonthStr 所点击的当月天数(string类型)
     */
    private void changeNowDate(String dayOfMonthStr) {
        try {
            int dayOfMonth = Integer.parseInt(dayOfMonthStr);
            // 获取选中天
            LocalDate localDate = LocalDate.of(
                    YEAR_OPTION[YEAR_BOX.getSelectedIndex()],
                    MONTH_OPTION[MONTH_BOX.getSelectedIndex()],
                    dayOfMonth);
            System.out.println("点击天数:" + localDate);
            nowDate = localDate;
            nowDateLabel.setText(localDate.toString());
            paintDay();
        } catch (NumberFormatException e) {
            System.err.println("日历数字按钮转化错误");
        }
    }

    public static void main(String[] args) {
        new CalendarTrain();
    }

}