const blockedChannels = ["MrBeast", "@MrBeast", "MrBeast Gaming"];
const blockedKeywords = ["mrbeast"];

function shouldHideVideo(title = "", channel = "") {
  const t = title.toLowerCase();
  const c = channel.toLowerCase();

  return (
    blockedChannels.some(b => c.includes(b.toLowerCase())) ||
    blockedKeywords.some(k => t.includes(k))
  );
}

// apply to youtube cards
function filterYouTubeVideos() {
  const videos = document.querySelectorAll("ytd-video-renderer, ytd-rich-item-renderer");

  videos.forEach(v => {
    const titleEl = v.querySelector("#video-title");
    const channelEl = v.querySelector("ytd-channel-name a");

    const title = titleEl?.innerText || "";
    const channel = channelEl?.innerText || "";

    if (shouldHideVideo(title, channel)) {
      v.style.display = "none";
    }
  });
}


const observer = new MutationObserver(() => {
  filterYouTubeVideos();
});

observer.observe(document.body, {
  childList: true,
  subtree: true
});

// initial run
filterYouTubeVideos();
