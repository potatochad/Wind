class AppListData {
    private val lists = mutableMapOf<String, LazyAppData>()

    operator fun get(listName: String): LazyAppData {
        return lists.getOrPut(listName) {
            LazyAppData(listName)
        }
    }
}
