ruleset {
    ruleset('rulesets/basic.xml') {
        'EmptyCatchBlock' {
            doNotApplyToClassNames = 'SOAPClient'
        }
    }
    ruleset('rulesets/braces.xml')
    ruleset('rulesets/concurrency.xml')
    ruleset('rulesets/design.xml')
    ruleset('rulesets/dry.xml') {
        exclude 'DuplicateStringLiteral'
    }
    ruleset('rulesets/exceptions.xml') {
        'CatchException' {
            doNotApplyToClassNames = 'HTTPClient,RESTClient,SOAPClient,ObjectHelper'
        }
    }
    ruleset('rulesets/generic.xml') {
        'StatelessClass' {
            doNotApplyToClassNames = 'HTTPClient,HTTPClientException,HTTPMethod,HTTPRequest,HTTPResponse,' +
                    'HTTPBasicAuthorization,RESTClient,ContentBuilder,Response,' +
                    'SOAPClient,SOAPFaultException,SOAPMessageBuilder,SOAPResponse'
        }
    }
    ruleset('rulesets/grails.xml')
    ruleset('rulesets/imports.xml')
    ruleset('rulesets/junit.xml')
    ruleset('rulesets/logging.xml')
    ruleset('rulesets/naming.xml') {
        'FieldName' {
            doNotApplyToClassNames = 'ContentType'
        }
        'ConfusingMethodName' {
            doNotApplyToClassNames='SOAPMessageBuilder,ContentBuilder'
        }
        'FactoryMethodName' {
            doNotApplyToClassNames='HTTPClient,RESTClient,SOAPClient'
        }
    }
    ruleset('rulesets/size.xml')
    ruleset('rulesets/unnecessary.xml') {
        'UnnecessaryGetter' {
            doNotApplyToClassNames = 'HTTPConnectionFactory,HTTPBasicAuthorization,HTTPRequest,' +
                    'ContentBuilder,RequestBuilder,ResponseBuilder,' +
                    'SOAPResponse,' +
                    'ObjectHelper'
        }
        exclude 'UnnecessarySemicolon'
        exclude 'UnnecessaryReturnKeywordRule'
        exclude 'UnnecessaryReturnKeyword'
        exclude 'UnnecessaryObjectReferences'
    }
    ruleset('rulesets/unused.xml')
}
