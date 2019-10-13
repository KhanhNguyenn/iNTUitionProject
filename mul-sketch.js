var s,score=0;
var scl=20, food;
var mode = 0;
const scrp = document.querySelector("p.score");

function setup(){
    createCanvas(600, 600);
    PickupLocation();
    s = new Snake("Snake1");
    as = new Snake("Snake2");
    as.total = [[28,28]];
    s.total = [[0,0]];
    frameRate(8);
}
function mod(n, m) {
  return ((n % m) + m) % m;
}
function PickupLocation(){
    food = [floor(random(1,29)),floor(random(1,29))];
}
function reset(){
    s.total = [[28,28]];
    score = 0;
    mode = 0;
    s.v = [1,0];
    as.v =[1,0];
    as.total = [[0,0]];
}

function draw(){
    
    background(15);

    if(s.death(s.total)){        
        mode = 1;
        console.log(1);
    }
    if(as.death(as.total)){
        mode = 2;
        console.log(2);
    }
    if(s.eat(food)||as.eat(food)){
        PickupLocation();
        score++;
        scrp.innerHTML="Score: "+score;
    }
    
        

    if (mode==0){
        s.move(s.v,s.total,s.total[0]);
        s.collide(as,2,1); // 2 means s win
        as.move(as.v,as.total,as.total[0]);
        as.collide(s,1,2); // 1 mean as win
        s.show([255,0,0]);
        as.show([0,0,255]);
    }else if(mode == 1){
        fill(255);
        textSize(40);
        text(as.name + " Win",200,300);
    } else{
        fill(255);
        textSize(40);
        text(s.name + " Win",200,300);
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
    if(!as.v[1]){
        if(keyCode===87){
          as.v = [0,-1];  
        }
        if(keyCode===83){
          as.v = [0,1];  
        }
    }
    if(!as.v[0]){
        if(keyCode===65){
          as.v = [-1,0];  
        }
        if(keyCode===68){
          as.v = [1,0];  
        }
    }
}
