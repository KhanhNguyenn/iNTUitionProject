function Snake(name){
    this.name=name;
    this.v = [1,0];
    this.total = [];
    this.grow = function(vel,total,head){
        this.total.unshift([mod((head[0]+vel[0]),30),mod((head[1]+vel[1]),30)]);
        
    }
    this.move = function(vel,total,head){
        this.grow(vel,total,head);
        this.total.pop();
    }
    this.eat = function(pos){
        var d = dist(this.total[0][0],this.total[0][1],pos[0],pos[1]);
        if(d<1){
            this.grow(this.v,this.total,this.total[0]);
            return true;
            }
        return false;
    }
    this.show = function(x){
        fill(x[0],x[1],x[2]);
        for(let i = 0; i<this.total.length;i++){
            rect(this.total[i][0]*scl,this.total[i][1]*scl,scl,scl);
        }
    }
    this.death = function(total){
        for(let i=0;i<total.length;i++){
            var d=dist(total[0][0],total[0][1],total[i][0],total[i][1]);
            if(d==0 && i!=0){
                return true;
            }
        }
        return false;
    }
    this.collide = function(ans,f,g){
        for(var i = 0; i<ans.total.length;i++){
            var d = dist(this.total[0][0],this.total[0][1],ans.total[i][0],ans.total[i][1]);
            if(i!=0 && d==0){
                
                for(let j=i;j<ans.total.length;j++){
                    this.total.unshift([this.total[0][0]+this.v[0],this.total[0][1]+this.v[1]]);
                    ans.total.pop();
                    }
            
                }
            if(i==0 && d==0){
                    if(this.total.length>ans.total.length){
                        mode = f;    
                        }
                    else if(this.total.length<ans.total.length){
                        mode = g;
                    }
            }
        }
    }
}
