export interface NavItem {
    label: string;
    href?: string;
    icon: string;
    isActive?: boolean;
    hasDropdown?: boolean;
    isOpen?: boolean;
    subItems?: NavItem[];
    visible?: boolean;
    action?: () => void;
}