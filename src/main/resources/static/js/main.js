document.getElementById("limit").addEventListener('change', ev => {
    const limitValue = ev.target.value
    window.location = '/home?limit=' + limitValue;
})
function add(element){
    const newID = generateID();
    const tag = document.getElementById(element + "s-box");
    const div = `<div class="input-group mb-3" id="${newID}"> 
  <input type="text" class="form-control" name="${element + 's'}" placeholder="${element}">
  <button class="btn btn-danger" type="button" onclick="deleteElement(${newID})">X</button>
</div>`;
    tag.insertAdjacentHTML('beforeend', div);
}

function deleteElement(id){
    document.getElementById(id).remove();
}
function generateID(){
    return new Date().getTime();
}