/**
 * 
 */
console.log("thise ")

console.log("this");
function toggleFunction() {
  if ($(".sidebar").is(":visible")) {
    // Sidebar is currently visible, so we're hiding it
    $(".sidebar").hide(200);
    $(".contain").css("margin-left", "0%");
  } else {
    // Sidebar is hidden, so we're showing it
    $(".sidebar").show(200);
    $(".contain").css("margin-left", "20%");
  }
}
