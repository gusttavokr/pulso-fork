export function enumToOptions<T extends Record<string, string>>(enumObj: T) {
    return Object.values(enumObj).map(value => ({
        label: formatLabel(value),
        value
    }));
}

function formatLabel(value: string) {
    return value
        .toLowerCase()
        .replace(/_/g, ' ')
        .replace(/\b\w/g, l => l.toUpperCase());
}
