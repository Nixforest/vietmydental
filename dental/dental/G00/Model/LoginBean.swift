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
    /**
     * Object instance
     */
    public static let shared: LoginBean = {
        let instance = LoginBean()
        return instance
    }()
    // MARK: Properties
    /** User token */
    var token:              String          = DomainConst.BLANK
    /** Id of role */
    var role_id:            String          = DomainConst.BLANK
    /** Flag need change pass */
    var need_change_pass:   String          = DomainConst.BLANK
    /** List menu */
    var menu:               [ConfigBean]    = [ConfigBean]()
    /** Pathological config */
    var pathological:       [ConfigBean]    = [ConfigBean]()
    /** Treatment status config */
    var status_treatment:   [ConfigBean]    = [ConfigBean]()
    /** Address config */
    var address_config:     [ConfigBean]    = [ConfigBean]()
    
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
        // Role id
        self.role_id = getString(json: jsonData, key: DomainConst.KEY_ROLE_ID)
        // Flag need change password
        self.need_change_pass = getString(json: jsonData, key: DomainConst.KEY_NEED_CHANGE_PASS)
        // Menu
        self.menu.append(contentsOf: getListConfig(json: jsonData, key: DomainConst.KEY_MENU))
        // Pathological
        self.pathological.append(contentsOf: getListConfig(json: jsonData, key: DomainConst.KEY_PATHOLOGICAL))
        // Treatment status config
        self.status_treatment.append(contentsOf: getListConfig(json: jsonData, key: DomainConst.KEY_STATUS_TREATMENT))        // Address config
        self.address_config.append(contentsOf: getListConfig(json: jsonData, key: DomainConst.KEY_ADDRESS_CONFIG))
    }
    
    /**
     * Get city by id
     * - parameter id:      Id of city
     * - returns: City model
     */
    public func getCityById(id: String) -> ConfigBean {
        for item in self.address_config {
            if item.id == id {
                return item
            }
        }
        return ConfigBean()
    }
    
    /**
     * Get district by id
     * - parameter id:          Id of district
     * - parameter cityId:      Id of city
     * - returns: District model
     */
    public func getDistrictById(id: String, cityId: String) -> ConfigBean {
        for item in self.getCityById(id: cityId).data {
            if item.id == id {
                return item
            }
        }
        return ConfigBean()
    }
    
    /**
     * Get ward by id
     * - parameter id:          Id of ward
     * - parameter cityId:      Id of city
     * - parameter districtId:  Id of district
     * - returns: Ward model
     */
    public func getWardById(id: String, cityId: String, districtId: String)
        -> ConfigBean {
            for item in self.getDistrictById(id: districtId, cityId: cityId).data {
                if item.id == id {
                    return item
                }
            }
            return ConfigBean()
    }
}
