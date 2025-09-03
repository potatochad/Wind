fun GeckoSession.setYouTubeFilter() {
    progressDelegate = object : GeckoSession.ProgressDelegate {
        override fun onProgressChange(session: GeckoSession, progress: Int) {
            if (progress == 100) {
                injectYouTubeJS()
            }
        }
    }
}


// Inject JS that removes YouTube content and dynamically handles new elements
private fun GeckoSession.injectYouTubeJS() {
    val js = """
    (function() {
        const url = window.location.href;

        if(url.includes('youtube.com')) {
            // Remove suggestions sidebar on video pages
            const suggestions = document.querySelector('#related');
            if(suggestions) suggestions.remove();

            // Remove search results
            const searchResults = document.querySelectorAll('ytd-item-section-renderer, ytd-video-renderer, ytd-channel-renderer');
            searchResults.forEach(el => el.remove());

            // Remove recommended videos under video
            const recommended = document.querySelector('#secondary');
            if(recommended) recommended.remove();

            // Dynamic observer for AJAX-loaded content
            const observer = new MutationObserver(mutations => {
                mutations.forEach(() => {
                    const suggestions = document.querySelector('#related');
                    if(suggestions) suggestions.remove();

                    const searchResults = document.querySelectorAll('ytd-item-section-renderer, ytd-video-renderer, ytd-channel-renderer');
                    searchResults.forEach(el => el.remove());
                });
            });
            observer.observe(document.body, { childList: true, subtree: true });
        }
    })();
    """
    loadUri("javascript:$js")
}

