//
//  LoginBean.swift
//  dental
//
//  Created by SPJ on 1/9/18.
//  Copyright © 2018 SPJ. All rights reserved.
//

import UIKit
import harpyframework

class LoginBean: ConfigBean {
    /** User token */
    var token:              String          = DomainConst.BLANK
    /** List menu */
    var menu:               [ConfigBean]    = [ConfigBean]()
    /** Id of role */
    var role_id:            String          = DomainConst.BLANK
    /** Flag need change pass */
    var need_change_pass:   String          = DomainConst.BLANK
    
    override public init() {
        super.init()
    }
    
    /**
     * Initializer
     * - parameter jsonData: List of data
     */
    public override init(jsonData: [String: AnyObject]) {
        super.init()
        // Token
        self.token = getString(json: jsonData, key: DomainConst.KEY_TOKEN)
        // Menu
        self.menu.append(contentsOf: getListConfig(json: jsonData, key: DomainConst.KEY_MENU))
        // Role id
        self.role_id = getString(json: jsonData, key: DomainConst.KEY_ROLE_ID)
        // Flag need change password
        self.need_change_pass = getString(json: jsonData, key: DomainConst.KEY_NEED_CHANGE_PASS)
    }
}
