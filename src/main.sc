require: requirements.sc
theme: /
    
    state: Start
        q!: $regex</start>
        a: Здравствуйте!
        
    state: checkDoctor
        q!: * ({$doctor * $medic} | $doctor) *
        if: $session.clientID
            a: $parseTree.text
        else:
            a: Пожалуйста, назовите ваш идентификационный номер.
        
    state: checkRequest
        q!: * $medic *
        if: $session.clientID
            go!: /specifySpecialty
        else:
            a: Пожалуйста, назовите ваш идентификационный номер.  
    
    state: authUser
        q!: * $digit *
        script:
            var text = $parseTree.text;
            if (el.match(/\d+/)){
                var number = el.match(/\d+/)[0];
                if (number.length > 9){
                    console.log("more 9");
                }
                else if (number.length < 9){
                    console.log("less 9");
                }
                else{
                    if (findClient(number))
                        $session.clientID = number
                }
            }
        if: $session.clientID && $session.speciality
            go!: /
    
    state: sayShedule
        a: Расписание
        
    state: specifySpecialty
        a: Уточните, пожалуйста, специальность врача, чтобы узнать расписание.
        
    state: specifyId
        a: Пожалуйста, назовите ваш идентификационный номер.
    
    state: NoMatch || noContext = true
        event!: noMatch
        random:
            a: Не смог разобрать :( Попробуете сказать иначе?
            a: Простите, я не понял вас. Давайте попробуем еще раз?
            a: Извините, я не понял. Попробуйте сформулировать по-другому   