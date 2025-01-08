# Load latest Debian image
FROM debian:latest

ARG USERNAME=vscode
ARG USER_UID=1000
ARG USER_GID=$USER_UID

# Start as the root user
USER root

# Ensure image is up to date
RUN apt update -y && \
    apt upgrade -y && \
    apt clean && \
    rm -rf /var/lib/apt/lists/*

# Install things needed to install Java versions we'll need
RUN export DEBIAN_FRONTEND=noninteractive && \
    apt update -y && \
    apt install -y --no-install-recommends ca-certificates wget apt-transport-https gnupg2 && \
    apt clean && \
    rm -rf /var/lib/apt/lists/*

# Install Eclipse Adoptium GPG key(s)
RUN wget -qO - https://packages.adoptium.net/artifactory/api/gpg/key/public | gpg --dearmor | tee /usr/share/keyrings/adoptium.pgp > /dev/null && \
    echo "deb [signed-by=/usr/share/keyrings/adoptium.pgp] https://packages.adoptium.net/artifactory/deb $(awk -F= '/^VERSION_CODENAME/{print$2}' /etc/os-release) main" | tee /etc/apt/sources.list.d/adoptium.list

# Install Java versions required
RUN export DEBIAN_FRONTEND=noninteractive && \
    apt update -y && \
    apt install -y --no-install-recommends temurin-17-jdk temurin-8-jdk && \
    apt clean && \
    rm -rf /var/lib/apt/lists/*

# Install various other pieces of software
RUN export DEBIAN_FRONTEND=noninteractive && \
    apt update -y && \
    apt install -y --no-install-recommends zsh git git-lfs vim neovim sudo && \
    apt clean && \
    rm -rf /var/lib/apt/lists/*

# Install Git LFS
RUN git lfs install --system

# Add our custom user
RUN groupadd --gid $USER_GID $USERNAME && \
    useradd --uid $USER_UID --gid $USER_GID -m $USERNAME

# Install sudo support for our custom user
RUN echo $USERNAME ALL=\(root\) NOPASSWD: ALL > /etc/sudoers.d/$USERNAME && \
    chmod 0440 /etc/sudoers.d/$USERNAME

# Switch to our custom user
USER $USERNAME
WORKDIR /home/$USERNAME

# Install Oh My Zsh
RUN sh -c "$(wget -O- https://raw.githubusercontent.com/ohmyzsh/ohmyzsh/master/tools/install.sh)" --unattended

# Configure Oh My Zsh
RUN zsh -ic "omz theme set blinks" && \
    zsh -ic "omz plugin enable debian git emoji gh git-lfs gradle sudo"
