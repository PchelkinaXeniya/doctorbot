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
            go!: /specifyId
        
    state: checkRequest
        q!: * $medic *
        if: $session.clientID
            go!: /specifySpecialty
        else:
            go!: /specifyId
    
    state: authUser
        q!: * $digit *
        script:
            parseDidgit($parseTree, $session, $temp);
        if: $temp.lengthId > 9
            go!: /numberLong
        elseif: $temp.lengthId < 9
            go!: /numberShort
        elseif: $session.clientID
            go!: /sayShedule
    
    state: sayShedule
        a: Расписание
        
    state: specifySpecialty
        a: Уточните, пожалуйста, специальность врача, чтобы узнать расписание.
        
    state: specifyId
        a: Пожалуйста, назовите ваш идентификационный номер.
        
    state: numberLong
        a: Вы назвали больше цифр, чем нужно. Попробуйте ещё раз.
        
    state: numberShort
        a: Вы назвали меньше цифр, чем нужно. Попробуйте ещё раз.
        
    state: operator
        a: Соединяю с оператором
        script:
            $response.replies.push({
                "type": "switch",
                "phoneNumber": "79123456789",
                "continueCall": true,
                "continueRecording": true
            });
    
    state: NoMatch || noContext = true
        event!: noMatch
        random:
            a: Не смог разобрать :( Попробуете сказать иначе?
            a: Простите, я не понял вас. Давайте попробуем еще раз?
            a: Извините, я не понял. Попробуйте сформулировать по-другому   