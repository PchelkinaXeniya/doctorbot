require: requirements.sc
theme: /
    
    state: Start
        q!: $regex</start>
        a: Здравствуйте!
        
    state: doctorFind
        q!: * $doctors *
        a: Есть такой