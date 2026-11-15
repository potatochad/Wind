@Composable
fun Center(ui: uiColumn){
	LazzyColumn(Mod.maxS()) { ui() }
}
