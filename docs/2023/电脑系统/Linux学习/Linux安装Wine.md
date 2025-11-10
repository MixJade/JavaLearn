# Linux安装Wine

> 2025-11-10 13:54:53

在 Linux Mint 系统中安装 Wine 并运行 Windows 专属安装包，核心步骤是 **安装 Wine → 配置 Wine 环境 → 运行 Windows 安装包**，以下是详细、可落地的操作指南（适用于 Linux Mint 20/21 系列，基于 Ubuntu 底层，步骤通用）：


### 一、前置准备：启用 32 位架构（关键！）
很多 Windows 软件是 32 位的，Wine 需要 32 位架构支持才能正常运行，先执行以下命令启用：
```bash
sudo dpkg --add-architecture i386
```


### 二、安装 Wine（推荐官方稳定版，兼容性最好）
Linux Mint 软件中心的 Wine 版本可能较旧，建议通过 Wine 官方仓库安装最新稳定版（以 Wine 9.0 为例，适配大部分软件）：

#### 步骤 1：添加 Wine 官方 GPG 密钥（验证安装包完整性）
```bash
sudo mkdir -pm755 /etc/apt/trusted.gpg.d
sudo wget -O /etc/apt/trusted.gpg.d/winehq-archive.key https://dl.winehq.org/wine-builds/winehq.key
```

#### 步骤 2：添加 Wine 官方仓库（根据 Mint 版本选择）
先确认你的 Linux Mint 对应的 Ubuntu 版本（Mint 20 → Ubuntu 20.04；Mint 21 → Ubuntu 22.04），执行以下对应命令：
- **Mint 21 系列（Ubuntu 22.04 底层）**：
  ```bash
  sudo wget -NP /etc/apt/sources.list.d/ https://dl.winehq.org/wine-builds/ubuntu/dists/jammy/winehq-jammy.sources
  ```
- **Mint 20 系列（Ubuntu 20.04 底层）**：
  ```bash
  sudo wget -NP /etc/apt/sources.list.d/ https://dl.winehq.org/wine-builds/ubuntu/dists/focal/winehq-focal.sources
  ```

#### 步骤 3：更新软件源并安装 Wine
```bash
# 更新软件包列表
sudo apt update

# 安装 Wine 稳定版（推荐）
sudo apt install -y --install-recommends winehq-stable
```

#### 步骤 4：验证 Wine 安装成功
安装完成后，执行以下命令查看版本，若显示版本号则说明安装成功：
```bash
wine --version
```
（正常输出示例：`wine-9.0`）


### 三、配置 Wine 环境（首次使用必做）
首次运行 Wine 时，需要初始化 Windows 模拟环境（生成“虚拟 C 盘”等目录）：
1. 在终端执行命令启动配置向导：
   ```bash
   winecfg
   ```
2. 弹出窗口后，按提示操作：
   - 首次运行会提示“安装 Mono（.NET 运行时）”和“安装 Gecko（浏览器引擎）”，**全部点击“安装”**（这两个组件是运行很多 Windows 软件的必需依赖）；
   - 安装完成后，会进入 Wine 配置界面，默认设置即可（若后续软件运行异常，可在这里调整 Windows 版本，比如改为 Windows 10/7）；
   - 点击“确定”关闭配置窗口，此时 Wine 已生成默认的模拟环境（目录：`~/.wine`，即“家目录/.wine”，其中 `drive_c` 对应 Windows 的 C 盘）。


### 四、安装 Windows 专属安装包（.exe 或 .msi 文件）
假设你已下载好 Windows 安装包（比如 `xxx.exe` 或 `xxx.msi`），分两种方式操作，推荐 **图形界面方式**（简单直观）：

#### 方式 1：图形界面操作（推荐）
1. 找到下载的 Windows 安装包（比如在“下载”文件夹）；
2. 右键点击安装包 → 选择 **“用 Wine Windows 程序加载器打开”**（默认已关联，若未关联则选择“打开方式”→“Wine Windows 程序加载器”）；
3. 后续步骤和在 Windows 中安装软件完全一致：
   - 点击“下一步”→ 接受许可协议 → 选择安装路径（默认路径是 `~/.wine/drive_c/Program Files/`，建议保持默认，避免中文路径）；
   - 点击“安装”→ 等待安装完成 → 点击“完成”。


#### 方式 2：终端命令操作（适合无图形界面或安装失败时）
1. 打开终端，进入 Windows 安装包所在目录（比如下载文件夹）：
   ```bash
   cd ~/下载  # 中文系统用这个，英文系统用 cd ~/Downloads
   ```
2. 执行以下命令运行安装包（将 `xxx.exe` 替换为实际安装包文件名）：
   - 对于 `.exe` 安装包：
     ```bash
     wine xxx.exe
     ```
   - 对于 `.msi` 安装包（Windows 安装程序包）：
     ```bash
     wine msiexec /i xxx.msi
     ```
3. 后续同样按照 Windows 安装向导完成操作即可。


### 五、运行已安装的 Windows 软件
安装完成后，有两种方式启动软件：
1. **图形界面启动**：
   
   - 打开 Linux Mint 菜单 → 找到“Wine”文件夹 → 里面会显示已安装的 Windows 软件图标，点击即可运行；
2. **终端启动**：
   - 先找到软件的可执行文件路径（默认在 `~/.wine/drive_c/Program Files/软件名称/` 下）；
   - 执行命令（将路径替换为实际可执行文件路径）：
     ```bash
     wine ~/.wine/drive_c/Program\ Files/软件名称/xxx.exe
     ```
     （路径中的空格需要用 `\` 转义，或用引号包裹路径：`wine "~/.wine/drive_c/Program Files/软件名称/xxx.exe"`）


### 六、常见问题解决（避坑关键）
1. **安装包点击无反应/启动失败**：
   - 检查是否启用 32 位架构（步骤一），很多老软件依赖 32 位环境；
   - 重新运行 `winecfg`，将“Windows 版本”改为 Windows 7（部分软件不兼容 Windows 10/11）；
   - 安装缺失的依赖：`sudo apt install -y winetricks`（Wine 工具集），然后运行 `winetricks`，按需安装 `dotnet48`（.NET 4.8）、`vcrun2019`（VC++ 运行库）等组件。

2. **中文乱码**：
   - 运行 `winecfg` → 切换到“区域”选项卡 → 将“语言”改为“中文（中国）”；
   - 若仍乱码，用 `winetricks` 安装 `cjkfonts`（中文字体）：`winetricks cjkfonts`。

3. **软件运行卡顿/崩溃**：
   - 避免安装路径包含中文或特殊字符（全程用英文路径）；
   - 关闭软件的“高 DPI 适配”（部分软件在 Linux 高分辨率屏幕下兼容不佳）。

4. **卸载 Windows 软件**：
   - 终端执行 `wine uninstaller`，会弹出 Windows 风格的卸载程序，选择要卸载的软件即可。


### 总结
Linux Mint 安装 Wine 并运行 Windows 软件的核心流程：  
**启用 32 位架构 → 安装 Wine 稳定版 → 初始化 Wine 环境 → 右键/终端运行安装包 → 启动软件**  
大部分常见 Windows 软件（如办公工具、小游戏、单机软件）都能通过这种方式正常运行，若遇到特殊软件（如需要管理员权限、依赖特定硬件的软件），可进一步查询 Wine 官方兼容性数据库（[Wine AppDB](https://appdb.winehq.org/)）确认适配情况。