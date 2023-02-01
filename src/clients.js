var clients = [
    "459886321",
    "152993148",
    "776351026",
    "776481026"
    ];
    
function findClient(id){
    return clients.find((iter) => iter === id) != undefined;   
}