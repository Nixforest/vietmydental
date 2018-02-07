//
//  CustomerBean.swift
//  dental
//
//  Created by SPJ on 2/2/18.
//  Copyright © 2018 SPJ. All rights reserved.
//

import UIKit
import harpyframework

class CustomerBean: ConfigBean {
    // MARK: Properties
    /** Gender */
    var gender:             String          = DomainConst.BLANK
    /** Age */
    var age:                String          = DomainConst.BLANK
    /** Phone */
    var phone:              String          = DomainConst.BLANK
    /** Address */
    var address:            String          = DomainConst.BLANK    
    
    override public init() {
        super.init()
    }
    
    /**
     * Initializer
     * - parameter jsonData: List of data
     */
    public override init(jsonData: [String: AnyObject]) {
        super.init(jsonData: jsonData)
        // Gender
        self.gender = getString(json: jsonData, key: DomainConst.KEY_GENDER)
        // Age
        self.age = getString(json: jsonData, key: DomainConst.KEY_AGE)
        // Phone
        self.phone = getString(json: jsonData, key: DomainConst.KEY_PHONE)
        // Address
        self.address = getString(json: jsonData, key: DomainConst.KEY_ADDRESS)
    }
}
