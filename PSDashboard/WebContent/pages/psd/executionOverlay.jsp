<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Example of Bootstrap 3 Modals</title>

</head>

<body>
	<div id="executionOverlay" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="gridSystemModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="overlayHeader">Select Nodes</h4>
				</div>
				
				<div class="modal-body">
					
					<div style="display: block;" id="paramDiv">
						<div class="row">
							<div class="col-md-8 col-md-offset-2">
								<div id="paramTableDiv"></div>
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-md-6 col-md-offset-1" style="text-align:center">
							<input type="radio" name="selExecutionOn" value="defaultNodes" id="defaultNodes" onclick="executionSelection();" checked="checked"/>Execute on Default Nodes
						</div>
						<div class="col-md-4" style="text-align:center">
							<input type="radio" name="selExecutionOn" value="selectNodes"  id="selectNodes" onclick="executionSelection();"/>Select Nodes
						</div>
					</div>
					<br />
					
					<div style="display: none;" id="nodeDiv">
						<div class="row">
							<div class="col-md-4 col-md-offset-1">
								<div id="onlineNodesTableDiv"></div>
							</div>
							<div class="col-md-4 col-md-offset-2">
								<div id="offlineNodesTableDiv"></div>
							</div>
						</div>
					</div>
				</div>

				<div class="modal-footer">
					<div id="exeOverlayButton"></div>
				</div>

			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->

	
	<script>
	$(document).ready(function(){
		$("#defaultNodes").click(function(){
			$("#nodeDiv").hide();
		});
		$("#selectNodes").click(function(){
			$("#nodeDiv").show();
		});
	});
		
	</script>

</body>
</html>                                		