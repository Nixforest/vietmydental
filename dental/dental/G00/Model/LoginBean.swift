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
    /** Address config */
    var address_config:     [ConfigBean]    = [ConfigBean]()
    /** Diagnosis config */
    var diagnosis:          [ConfigBean]    = [ConfigBean]()
    /** Treatment type config */
    var treatment:          ListConfigBean  = ListConfigBean()
    /** Teeth config */
    var teeth:              [ConfigBean]    = [ConfigBean]()
    
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
        self.address_config.append(contentsOf: getListConfig(json: jsonData, key: DomainConst.KEY_ADDRESS_CONFIG))
        // Diagnosis
        self.diagnosis.append(contentsOf: getListConfig(json: jsonData, key: DomainConst.KEY_DIAGNOSIS))
        // Treatment
        if let dataArr = jsonData[DomainConst.KEY_TREATMENT] as? [[String: AnyObject]] {
            self.treatment = ListConfigBean.init(jsonData: dataArr)
        }
        // Teeth
        self.teeth.append(contentsOf: getListConfig(json: jsonData, key: DomainConst.KEY_TEETH))
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
    
    /**
     * Get diagnosis config value
     * - parameter id:          Id of config
     */
    public func getDiagnosisConfig(id: String) -> String {
        for item in self.diagnosis {
            if item.id == id {
                return item.name
            }
        }
        return DomainConst.BLANK
    }
        
    /**
     * Get treatment config value
     * - parameter id: Id of config
     */
    public func getTreatmentConfig(id: String) -> String {
        for item in self.treatment.getData() {
            for child in item._dataExt {
                if child.id == id {
                    return child.name
                }
            }
        }
        return DomainConst.BLANK
    }
    
    /**
     * Get teeth config value
     * - parameter id:          Id of config
     */
    public func getTeethConfig(id: String) -> String {
        for item in self.teeth {
            if item.id == id {
                return item.name
            }
        }
        return DomainConst.BLANK
    }
    
    /**
     * Get update text string
     * - returns: String
     */
    public func getUpdateText() -> String {
        return DomainConst.CONTENT00555
    }
}
