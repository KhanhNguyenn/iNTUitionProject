var s,score=0;
var scl=20, food;
var mode = 0;
const scrp = document.querySelector("p.score");
function setup(){
    createCanvas(600, 600);
    PickupLocation();
    s = new Snake();
    s.total=[[0,0]];
    frameRate(10);
}
function mod(n, m) {
  return ((n % m) + m) % m;
}
function PickupLocation(){
    food = [floor(random(1,29)),floor(random(1,29))];
}
function reset(){
    s.total = [[0,0]];
    score = 0;
    mode = 0;
    s.v = [1,0];
}

function draw(){
    
    background(15);

    if(s.death(s.total)){        
        mode = 1;
    }
    if(s.eat(food)){
        PickupLocation();
        score++;
        scrp.innerHTML="Score: "+score;
    }
    s.move(s.v,s.total,s.total[0]);
    if (mode==0){
        s.show([255,0,0]);
    }else{
        fill(255);
        textSize(40);
        text("Game Over",220,300);
    }
    
    fill(0,255,0);
    rect(food[0]*scl,food[1]*scl,scl,scl);

        
}
function keyPressed() {
    if(!s.v[1]){
        if(keyCode===UP_ARROW){
          s.v = [0,-1];  
        }
        if(keyCode===DOWN_ARROW){
          s.v = [0,1];  
        }
    }
    if(!s.v[0]){
        if(keyCode===LEFT_ARROW){
          s.v = [-1,0];  
        }
        if(keyCode===RIGHT_ARROW){
          s.v = [1,0];  
        }
    }
}
