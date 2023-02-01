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
            a: Уточните, пожалуйста, специальность врача, чтобы узнать расписание.
        else:
            a: Пожалуйста, назовите ваш идентификационный номер.  
    
    state: authUser
        q!: * $digit *
    
    
    
    state: NoMatch || noContext = true
        event!: noMatch
        random:
            a: Не смог разобрать :( Попробуете сказать иначе?
            a: Простите, я не понял вас. Давайте попробуем еще раз?
            a: Извините, я не понял. Попробуйте сформулировать по-другому   